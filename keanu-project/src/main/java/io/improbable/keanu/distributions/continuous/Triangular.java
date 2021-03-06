package io.improbable.keanu.distributions.continuous;

import com.google.common.base.Preconditions;
import io.improbable.keanu.KeanuRandom;
import io.improbable.keanu.distributions.ContinuousDistribution;
import io.improbable.keanu.distributions.hyperparam.Diffs;
import io.improbable.keanu.tensor.dbl.DoubleTensor;
import io.improbable.keanu.vertices.tensor.number.floating.dbl.DoublePlaceholderVertex;
import io.improbable.keanu.vertices.tensor.number.floating.dbl.DoubleVertex;

public class Triangular implements ContinuousDistribution {

    private final DoubleTensor xMin;
    private final DoubleTensor xMax;
    private final DoubleTensor c;

    public static ContinuousDistribution withParameters(DoubleTensor xMin, DoubleTensor xMax, DoubleTensor c) {
        return new Triangular(xMin, xMax, c);
    }

    private Triangular(DoubleTensor xMin, DoubleTensor xMax, DoubleTensor c) {
        this.xMin = xMin;
        this.xMax = xMax;
        this.c = c;
    }

    @Override
    public DoubleTensor sample(long[] shape, KeanuRandom random) {
        Preconditions.checkArgument(c.greaterThanOrEqual(xMin).allTrue().scalar() && c.lessThanOrEqual(xMax).allTrue().scalar(),
            "center must be between xMin and xMax. c: " + c + " xMin: " + xMin + " xMax: " + xMax);

        final DoubleTensor p = random.nextDouble(shape);
        final DoubleTensor q = p.unaryMinus().plusInPlace(1.0);
        final DoubleTensor range = xMax.minus(xMin);

        final DoubleTensor conditional = (c.minus(xMin)).divInPlace(xMax.minus(xMin));

        final DoubleTensor lessThan = xMin.plus((range.times(c.minus(xMin).timesInPlace(p))).sqrtInPlace());
        final DoubleTensor greaterThan = xMax.minus((range.timesInPlace(xMax.minus(c).timesInPlace(q))).sqrtInPlace());

        final DoubleTensor lessThanMask = p.lessThanOrEqualToMask(conditional);
        final DoubleTensor greaterThanMask = p.greaterThanMask(conditional);

        return (lessThan.timesInPlace(lessThanMask).plusInPlace(greaterThan.timesInPlace(greaterThanMask)));
    }

    @Override
    public DoubleTensor logProb(DoubleTensor x) {
        final DoubleTensor range = xMax.minus(xMin);

        final DoubleTensor conditionalFirstHalf = x.greaterThanMask(xMin);
        final DoubleTensor conditionalSecondHalf = x.lessThanMask(c);
        final DoubleTensor conditionalAnd = conditionalFirstHalf.timesInPlace(conditionalSecondHalf);
        final DoubleTensor conditionalResult = range.reciprocal().timesInPlace(2.).timesInPlace(x.minus(xMin)).divInPlace(c.minus(xMin));

        final DoubleTensor elseIfConditionalFirstHalf = x.greaterThanMask(c);
        final DoubleTensor elseIfConditionalSecondHalf = x.lessThanOrEqualToMask(xMax);
        final DoubleTensor elseIfConditionalAnd = elseIfConditionalFirstHalf.timesInPlace(elseIfConditionalSecondHalf);
        final DoubleTensor elseIfConditionalResult = range.reciprocalInPlace().timesInPlace(2.).timesInPlace(xMax.minus(x)).divInPlace(xMax.minus(c));

        return (conditionalResult.timesInPlace(conditionalAnd).plusInPlace(elseIfConditionalResult.timesInPlace(elseIfConditionalAnd))).logInPlace();
    }

    public static DoubleVertex logProbOutput(DoublePlaceholderVertex x, DoublePlaceholderVertex xMin, DoublePlaceholderVertex xMax, DoublePlaceholderVertex c) {
        final DoubleVertex range = xMax.minus(xMin);

        final DoubleVertex conditionalFirstHalf = x.greaterThanMask(xMin);
        final DoubleVertex conditionalSecondHalf = x.lessThanMask(c);
        final DoubleVertex conditionalAnd = conditionalFirstHalf.times(conditionalSecondHalf);
        final DoubleVertex conditionalResult = range.reverseDiv(1.).times(2.).times(x.minus(xMin)).div(c.minus(xMin));

        final DoubleVertex elseIfConditionalFirstHalf = x.greaterThanMask(c);
        final DoubleVertex elseIfConditionalSecondHalf = x.lessThanOrEqualToMask(xMax);
        final DoubleVertex elseIfConditionalAnd = elseIfConditionalFirstHalf.times(elseIfConditionalSecondHalf);
        final DoubleVertex elseIfConditionalResult = range.reverseDiv(1.).times(2.).times(xMax.minus(x)).div(xMax.minus(c));

        return (conditionalResult.times(conditionalAnd).plus(elseIfConditionalResult.times(elseIfConditionalAnd))).log();
    }

    public Diffs dLogProb(DoubleTensor x) {
        throw new UnsupportedOperationException();
    }

}
