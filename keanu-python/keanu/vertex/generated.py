## This is a generated file. DO NOT EDIT.

from typing import Collection, Optional
from py4j.java_gateway import java_import
from keanu.context import KeanuContext
from .base import Vertex, Double, Integer, Boolean, vertex_constructor_param_types
from keanu.vertex.label import _VertexLabel
from keanu.vartypes import (
    tensor_arg_types,
    shape_types
)
from .vertex_casting import (
    do_vertex_cast,
    do_inferred_vertex_cast,
    cast_to_double_tensor,
    cast_to_integer_tensor,
    cast_to_boolean_tensor,
    cast_to_double,
    cast_to_integer,
    cast_to_boolean,
    cast_to_long_array,
    cast_to_boolean_array,
    cast_to_int_array,
    cast_to_vertex_array,
)

context = KeanuContext()


def cast_to_double_vertex(input: vertex_constructor_param_types) -> Vertex:
    return do_vertex_cast(ConstantDouble, input)


def cast_to_integer_vertex(input: vertex_constructor_param_types) -> Vertex:
    return do_vertex_cast(ConstantInteger, input)


def cast_to_boolean_vertex(input: vertex_constructor_param_types) -> Vertex:
    return do_vertex_cast(ConstantBoolean, input)


def cast_to_vertex(input: vertex_constructor_param_types) -> Vertex:
    return do_inferred_vertex_cast({bool: ConstantBoolean, int: ConstantInteger, float: ConstantDouble}, input)


java_import(context.jvm_view(), "io.improbable.keanu.vertices.tensor.BroadcastVertex")
java_import(context.jvm_view(), "io.improbable.keanu.vertices.tensor.DiagPartVertex")
java_import(context.jvm_view(), "io.improbable.keanu.vertices.tensor.DiagVertex")
java_import(context.jvm_view(), "io.improbable.keanu.vertices.tensor.FillTriangularVertex")
java_import(context.jvm_view(), "io.improbable.keanu.vertices.tensor.PermuteVertex")
java_import(context.jvm_view(), "io.improbable.keanu.vertices.tensor.ReshapeVertex")
java_import(context.jvm_view(), "io.improbable.keanu.vertices.tensor.SliceVertex")
java_import(context.jvm_view(), "io.improbable.keanu.vertices.tensor.StridedSliceVertex")
java_import(context.jvm_view(), "io.improbable.keanu.vertices.tensor.TakeVertex")
java_import(context.jvm_view(), "io.improbable.keanu.vertices.tensor.TriLowerVertex")
java_import(context.jvm_view(), "io.improbable.keanu.vertices.tensor.TriUpperVertex")
java_import(context.jvm_view(), "io.improbable.keanu.vertices.tensor.TrianglePartVertex")
java_import(context.jvm_view(), "io.improbable.keanu.vertices.tensor.WhereVertex")
java_import(context.jvm_view(), "io.improbable.keanu.vertices.tensor.bool.nonprobabilistic.BooleanProxyVertex")
java_import(context.jvm_view(), "io.improbable.keanu.vertices.tensor.bool.nonprobabilistic.CastNumberToBooleanVertex")
java_import(context.jvm_view(), "io.improbable.keanu.vertices.tensor.bool.nonprobabilistic.CastToBooleanVertex")
java_import(context.jvm_view(), "io.improbable.keanu.vertices.tensor.bool.nonprobabilistic.ConstantBooleanVertex")
java_import(context.jvm_view(), "io.improbable.keanu.vertices.tensor.bool.nonprobabilistic.operators.binary.AndBinaryVertex")
java_import(context.jvm_view(), "io.improbable.keanu.vertices.tensor.bool.nonprobabilistic.operators.binary.OrBinaryVertex")
java_import(context.jvm_view(), "io.improbable.keanu.vertices.tensor.bool.nonprobabilistic.operators.binary.XorBinaryVertex")
java_import(context.jvm_view(), "io.improbable.keanu.vertices.tensor.bool.nonprobabilistic.operators.binary.compare.EqualsVertex")
java_import(context.jvm_view(), "io.improbable.keanu.vertices.tensor.bool.nonprobabilistic.operators.binary.compare.GreaterThanOrEqualVertex")
java_import(context.jvm_view(), "io.improbable.keanu.vertices.tensor.bool.nonprobabilistic.operators.binary.compare.GreaterThanVertex")
java_import(context.jvm_view(), "io.improbable.keanu.vertices.tensor.bool.nonprobabilistic.operators.binary.compare.LessThanOrEqualVertex")
java_import(context.jvm_view(), "io.improbable.keanu.vertices.tensor.bool.nonprobabilistic.operators.binary.compare.LessThanVertex")
java_import(context.jvm_view(), "io.improbable.keanu.vertices.tensor.bool.nonprobabilistic.operators.binary.compare.NotEqualsVertex")
java_import(context.jvm_view(), "io.improbable.keanu.vertices.tensor.bool.nonprobabilistic.operators.binary.compare.NumericalEqualsVertex")
java_import(context.jvm_view(), "io.improbable.keanu.vertices.tensor.bool.nonprobabilistic.operators.multiple.BooleanConcatenationVertex")
java_import(context.jvm_view(), "io.improbable.keanu.vertices.tensor.bool.nonprobabilistic.operators.multiple.BooleanToDoubleMaskVertex")
java_import(context.jvm_view(), "io.improbable.keanu.vertices.tensor.bool.nonprobabilistic.operators.multiple.BooleanToIntegerMaskVertex")
java_import(context.jvm_view(), "io.improbable.keanu.vertices.tensor.bool.nonprobabilistic.operators.unary.AllFalseVertex")
java_import(context.jvm_view(), "io.improbable.keanu.vertices.tensor.bool.nonprobabilistic.operators.unary.AllTrueVertex")
java_import(context.jvm_view(), "io.improbable.keanu.vertices.tensor.bool.nonprobabilistic.operators.unary.AnyFalseVertex")
java_import(context.jvm_view(), "io.improbable.keanu.vertices.tensor.bool.nonprobabilistic.operators.unary.AnyTrueVertex")
java_import(context.jvm_view(), "io.improbable.keanu.vertices.tensor.bool.nonprobabilistic.operators.unary.IsFiniteVertex")
java_import(context.jvm_view(), "io.improbable.keanu.vertices.tensor.bool.nonprobabilistic.operators.unary.IsInfiniteVertex")
java_import(context.jvm_view(), "io.improbable.keanu.vertices.tensor.bool.nonprobabilistic.operators.unary.IsNaNVertex")
java_import(context.jvm_view(), "io.improbable.keanu.vertices.tensor.bool.nonprobabilistic.operators.unary.IsNegativeInfinityVertex")
java_import(context.jvm_view(), "io.improbable.keanu.vertices.tensor.bool.nonprobabilistic.operators.unary.IsPositiveInfinityVertex")
java_import(context.jvm_view(), "io.improbable.keanu.vertices.tensor.bool.nonprobabilistic.operators.unary.NotBinaryVertex")
java_import(context.jvm_view(), "io.improbable.keanu.vertices.tensor.bool.nonprobabilistic.operators.unary.NotNaNVertex")
java_import(context.jvm_view(), "io.improbable.keanu.vertices.tensor.bool.probabilistic.BernoulliVertex")
java_import(context.jvm_view(), "io.improbable.keanu.vertices.tensor.generic.nonprobabilistic.PrintVertex")
java_import(context.jvm_view(), "io.improbable.keanu.vertices.tensor.number.fixed.intgr.nonprobabilistic.CastNumberToIntegerVertex")
java_import(context.jvm_view(), "io.improbable.keanu.vertices.tensor.number.fixed.intgr.nonprobabilistic.ConstantIntegerVertex")
java_import(context.jvm_view(), "io.improbable.keanu.vertices.tensor.number.fixed.intgr.nonprobabilistic.IntegerProxyVertex")
java_import(context.jvm_view(), "io.improbable.keanu.vertices.tensor.number.fixed.intgr.nonprobabilistic.operators.multiple.IntegerConcatenationVertex")
java_import(context.jvm_view(), "io.improbable.keanu.vertices.tensor.number.fixed.intgr.nonprobabilistic.operators.unary.ArgMaxVertex")
java_import(context.jvm_view(), "io.improbable.keanu.vertices.tensor.number.fixed.intgr.nonprobabilistic.operators.unary.ArgMinVertex")
java_import(context.jvm_view(), "io.improbable.keanu.vertices.tensor.number.fixed.intgr.nonprobabilistic.operators.unary.NaNArgMaxVertex")
java_import(context.jvm_view(), "io.improbable.keanu.vertices.tensor.number.fixed.intgr.nonprobabilistic.operators.unary.NaNArgMinVertex")
java_import(context.jvm_view(), "io.improbable.keanu.vertices.tensor.number.fixed.intgr.probabilistic.BinomialVertex")
java_import(context.jvm_view(), "io.improbable.keanu.vertices.tensor.number.fixed.intgr.probabilistic.GeometricVertex")
java_import(context.jvm_view(), "io.improbable.keanu.vertices.tensor.number.fixed.intgr.probabilistic.MultinomialVertex")
java_import(context.jvm_view(), "io.improbable.keanu.vertices.tensor.number.fixed.intgr.probabilistic.PoissonVertex")
java_import(context.jvm_view(), "io.improbable.keanu.vertices.tensor.number.fixed.intgr.probabilistic.UniformIntVertex")
java_import(context.jvm_view(), "io.improbable.keanu.vertices.tensor.number.fixed.operators.unary.ModVertex")
java_import(context.jvm_view(), "io.improbable.keanu.vertices.tensor.number.floating.dbl.nonprobabilistic.CastNumberToDoubleVertex")
java_import(context.jvm_view(), "io.improbable.keanu.vertices.tensor.number.floating.dbl.nonprobabilistic.ConstantDoubleVertex")
java_import(context.jvm_view(), "io.improbable.keanu.vertices.tensor.number.floating.dbl.nonprobabilistic.DoubleProxyVertex")
java_import(context.jvm_view(), "io.improbable.keanu.vertices.tensor.number.floating.dbl.nonprobabilistic.operators.multiple.ConcatenationVertex")
java_import(context.jvm_view(), "io.improbable.keanu.vertices.tensor.number.floating.dbl.probabilistic.BetaVertex")
java_import(context.jvm_view(), "io.improbable.keanu.vertices.tensor.number.floating.dbl.probabilistic.CauchyVertex")
java_import(context.jvm_view(), "io.improbable.keanu.vertices.tensor.number.floating.dbl.probabilistic.ChiSquaredVertex")
java_import(context.jvm_view(), "io.improbable.keanu.vertices.tensor.number.floating.dbl.probabilistic.DirichletVertex")
java_import(context.jvm_view(), "io.improbable.keanu.vertices.tensor.number.floating.dbl.probabilistic.ExponentialVertex")
java_import(context.jvm_view(), "io.improbable.keanu.vertices.tensor.number.floating.dbl.probabilistic.GammaVertex")
java_import(context.jvm_view(), "io.improbable.keanu.vertices.tensor.number.floating.dbl.probabilistic.GaussianVertex")
java_import(context.jvm_view(), "io.improbable.keanu.vertices.tensor.number.floating.dbl.probabilistic.HalfCauchyVertex")
java_import(context.jvm_view(), "io.improbable.keanu.vertices.tensor.number.floating.dbl.probabilistic.HalfGaussianVertex")
java_import(context.jvm_view(), "io.improbable.keanu.vertices.tensor.number.floating.dbl.probabilistic.InverseGammaVertex")
java_import(context.jvm_view(), "io.improbable.keanu.vertices.tensor.number.floating.dbl.probabilistic.KDEVertex")
java_import(context.jvm_view(), "io.improbable.keanu.vertices.tensor.number.floating.dbl.probabilistic.LaplaceVertex")
java_import(context.jvm_view(), "io.improbable.keanu.vertices.tensor.number.floating.dbl.probabilistic.LogNormalVertex")
java_import(context.jvm_view(), "io.improbable.keanu.vertices.tensor.number.floating.dbl.probabilistic.LogisticVertex")
java_import(context.jvm_view(), "io.improbable.keanu.vertices.tensor.number.floating.dbl.probabilistic.MultivariateGaussianVertex")
java_import(context.jvm_view(), "io.improbable.keanu.vertices.tensor.number.floating.dbl.probabilistic.ParetoVertex")
java_import(context.jvm_view(), "io.improbable.keanu.vertices.tensor.number.floating.dbl.probabilistic.StudentTVertex")
java_import(context.jvm_view(), "io.improbable.keanu.vertices.tensor.number.floating.dbl.probabilistic.TriangularVertex")
java_import(context.jvm_view(), "io.improbable.keanu.vertices.tensor.number.floating.dbl.probabilistic.UniformVertex")
java_import(context.jvm_view(), "io.improbable.keanu.vertices.tensor.number.floating.operators.binary.ArcTan2Vertex")
java_import(context.jvm_view(), "io.improbable.keanu.vertices.tensor.number.floating.operators.binary.LogAddExp2Vertex")
java_import(context.jvm_view(), "io.improbable.keanu.vertices.tensor.number.floating.operators.binary.LogAddExpVertex")
java_import(context.jvm_view(), "io.improbable.keanu.vertices.tensor.number.floating.operators.binary.SafeLogTimesVertex")
java_import(context.jvm_view(), "io.improbable.keanu.vertices.tensor.number.floating.operators.unary.ArcCosVertex")
java_import(context.jvm_view(), "io.improbable.keanu.vertices.tensor.number.floating.operators.unary.ArcCoshVertex")
java_import(context.jvm_view(), "io.improbable.keanu.vertices.tensor.number.floating.operators.unary.ArcSinVertex")
java_import(context.jvm_view(), "io.improbable.keanu.vertices.tensor.number.floating.operators.unary.ArcSinhVertex")
java_import(context.jvm_view(), "io.improbable.keanu.vertices.tensor.number.floating.operators.unary.ArcTanVertex")
java_import(context.jvm_view(), "io.improbable.keanu.vertices.tensor.number.floating.operators.unary.ArcTanhVertex")
java_import(context.jvm_view(), "io.improbable.keanu.vertices.tensor.number.floating.operators.unary.CeilVertex")
java_import(context.jvm_view(), "io.improbable.keanu.vertices.tensor.number.floating.operators.unary.CholeskyDecompositionVertex")
java_import(context.jvm_view(), "io.improbable.keanu.vertices.tensor.number.floating.operators.unary.CholeskyInverseVertex")
java_import(context.jvm_view(), "io.improbable.keanu.vertices.tensor.number.floating.operators.unary.CosVertex")
java_import(context.jvm_view(), "io.improbable.keanu.vertices.tensor.number.floating.operators.unary.CoshVertex")
java_import(context.jvm_view(), "io.improbable.keanu.vertices.tensor.number.floating.operators.unary.DigammaVertex")
java_import(context.jvm_view(), "io.improbable.keanu.vertices.tensor.number.floating.operators.unary.Exp2Vertex")
java_import(context.jvm_view(), "io.improbable.keanu.vertices.tensor.number.floating.operators.unary.ExpM1Vertex")
java_import(context.jvm_view(), "io.improbable.keanu.vertices.tensor.number.floating.operators.unary.ExpVertex")
java_import(context.jvm_view(), "io.improbable.keanu.vertices.tensor.number.floating.operators.unary.FloorVertex")
java_import(context.jvm_view(), "io.improbable.keanu.vertices.tensor.number.floating.operators.unary.Log10Vertex")
java_import(context.jvm_view(), "io.improbable.keanu.vertices.tensor.number.floating.operators.unary.Log1pVertex")
java_import(context.jvm_view(), "io.improbable.keanu.vertices.tensor.number.floating.operators.unary.Log2Vertex")
java_import(context.jvm_view(), "io.improbable.keanu.vertices.tensor.number.floating.operators.unary.LogGammaVertex")
java_import(context.jvm_view(), "io.improbable.keanu.vertices.tensor.number.floating.operators.unary.LogVertex")
java_import(context.jvm_view(), "io.improbable.keanu.vertices.tensor.number.floating.operators.unary.MatrixDeterminantVertex")
java_import(context.jvm_view(), "io.improbable.keanu.vertices.tensor.number.floating.operators.unary.MatrixInverseVertex")
java_import(context.jvm_view(), "io.improbable.keanu.vertices.tensor.number.floating.operators.unary.MeanVertex")
java_import(context.jvm_view(), "io.improbable.keanu.vertices.tensor.number.floating.operators.unary.ReplaceNaNVertex")
java_import(context.jvm_view(), "io.improbable.keanu.vertices.tensor.number.floating.operators.unary.RoundVertex")
java_import(context.jvm_view(), "io.improbable.keanu.vertices.tensor.number.floating.operators.unary.SigmoidVertex")
java_import(context.jvm_view(), "io.improbable.keanu.vertices.tensor.number.floating.operators.unary.SinVertex")
java_import(context.jvm_view(), "io.improbable.keanu.vertices.tensor.number.floating.operators.unary.SinhVertex")
java_import(context.jvm_view(), "io.improbable.keanu.vertices.tensor.number.floating.operators.unary.StandardDeviationVertex")
java_import(context.jvm_view(), "io.improbable.keanu.vertices.tensor.number.floating.operators.unary.StandardizeVertex")
java_import(context.jvm_view(), "io.improbable.keanu.vertices.tensor.number.floating.operators.unary.TanVertex")
java_import(context.jvm_view(), "io.improbable.keanu.vertices.tensor.number.floating.operators.unary.TanhVertex")
java_import(context.jvm_view(), "io.improbable.keanu.vertices.tensor.number.floating.operators.unary.TrigammaVertex")
java_import(context.jvm_view(), "io.improbable.keanu.vertices.tensor.number.operators.binary.AdditionVertex")
java_import(context.jvm_view(), "io.improbable.keanu.vertices.tensor.number.operators.binary.DifferenceVertex")
java_import(context.jvm_view(), "io.improbable.keanu.vertices.tensor.number.operators.binary.DivisionVertex")
java_import(context.jvm_view(), "io.improbable.keanu.vertices.tensor.number.operators.binary.GreaterThanMaskVertex")
java_import(context.jvm_view(), "io.improbable.keanu.vertices.tensor.number.operators.binary.GreaterThanOrEqualToMaskVertex")
java_import(context.jvm_view(), "io.improbable.keanu.vertices.tensor.number.operators.binary.LessThanMaskVertex")
java_import(context.jvm_view(), "io.improbable.keanu.vertices.tensor.number.operators.binary.LessThanOrEqualToMaskVertex")
java_import(context.jvm_view(), "io.improbable.keanu.vertices.tensor.number.operators.binary.MatrixMultiplicationVertex")
java_import(context.jvm_view(), "io.improbable.keanu.vertices.tensor.number.operators.binary.MaxVertex")
java_import(context.jvm_view(), "io.improbable.keanu.vertices.tensor.number.operators.binary.MinVertex")
java_import(context.jvm_view(), "io.improbable.keanu.vertices.tensor.number.operators.binary.MultiplicationVertex")
java_import(context.jvm_view(), "io.improbable.keanu.vertices.tensor.number.operators.binary.PowerVertex")
java_import(context.jvm_view(), "io.improbable.keanu.vertices.tensor.number.operators.binary.TensorMultiplicationVertex")
java_import(context.jvm_view(), "io.improbable.keanu.vertices.tensor.number.operators.ternary.SetWithMaskVertex")
java_import(context.jvm_view(), "io.improbable.keanu.vertices.tensor.number.operators.unary.AbsVertex")
java_import(context.jvm_view(), "io.improbable.keanu.vertices.tensor.number.operators.unary.CumProdVertex")
java_import(context.jvm_view(), "io.improbable.keanu.vertices.tensor.number.operators.unary.CumSumVertex")
java_import(context.jvm_view(), "io.improbable.keanu.vertices.tensor.number.operators.unary.MaxUnaryVertex")
java_import(context.jvm_view(), "io.improbable.keanu.vertices.tensor.number.operators.unary.MinUnaryVertex")
java_import(context.jvm_view(), "io.improbable.keanu.vertices.tensor.number.operators.unary.ProductVertex")
java_import(context.jvm_view(), "io.improbable.keanu.vertices.tensor.number.operators.unary.SignVertex")
java_import(context.jvm_view(), "io.improbable.keanu.vertices.tensor.number.operators.unary.SumVertex")
java_import(context.jvm_view(), "io.improbable.keanu.vertices.utility.AssertVertex")


def Broadcast(input_vertex: vertex_constructor_param_types, to_shape: Collection[int], label: Optional[str]=None) -> Vertex:
    return Vertex(context.jvm_view().BroadcastVertex, label, cast_to_vertex(input_vertex), cast_to_long_array(to_shape))


def DiagPart(input_vertex: vertex_constructor_param_types, label: Optional[str]=None) -> Vertex:
    return Vertex(context.jvm_view().DiagPartVertex, label, cast_to_vertex(input_vertex))


def Diag(input_vertex: vertex_constructor_param_types, label: Optional[str]=None) -> Vertex:
    return Vertex(context.jvm_view().DiagVertex, label, cast_to_vertex(input_vertex))


def FillTriangular(input_vertex: vertex_constructor_param_types, fill_upper: bool, fill_lower: bool, label: Optional[str]=None) -> Vertex:
    return Vertex(context.jvm_view().FillTriangularVertex, label, cast_to_vertex(input_vertex), cast_to_boolean(fill_upper), cast_to_boolean(fill_lower))


def Permute(input_vertex: vertex_constructor_param_types, rearrange: Collection[int], label: Optional[str]=None) -> Vertex:
    return Vertex(context.jvm_view().PermuteVertex, label, cast_to_vertex(input_vertex), cast_to_int_array(rearrange))


def Reshape(input_vertex: vertex_constructor_param_types, proposed_shape: Collection[int], label: Optional[str]=None) -> Vertex:
    return Vertex(context.jvm_view().ReshapeVertex, label, cast_to_vertex(input_vertex), cast_to_long_array(proposed_shape))


def Slice(input_vertex: vertex_constructor_param_types, dimension: int, index: int, label: Optional[str]=None) -> Vertex:
    """
    Takes the slice along a given dimension and index of a vertex
    
    :param input_vertex: the input vertex
    :param dimension: the dimension to extract along
    :param index: the index of extraction
    """
    return Vertex(context.jvm_view().SliceVertex, label, cast_to_vertex(input_vertex), cast_to_integer(dimension), cast_to_integer(index))


def StridedSlice(input_vertex: vertex_constructor_param_types, start: Collection[int], end: Collection[int], stride: Collection[int], ellipsis: int, upper_bound_stop: Collection[bool], drop_dimension: Collection[bool], label: Optional[str]=None) -> Vertex:
    return Vertex(context.jvm_view().StridedSliceVertex, label, cast_to_vertex(input_vertex), cast_to_long_array(start), cast_to_long_array(end), cast_to_long_array(stride), cast_to_integer(ellipsis), cast_to_boolean_array(upper_bound_stop), cast_to_boolean_array(drop_dimension))


def Take(input_vertex: vertex_constructor_param_types, index: Collection[int], label: Optional[str]=None) -> Vertex:
    return Vertex(context.jvm_view().TakeVertex, label, cast_to_vertex(input_vertex), cast_to_long_array(index))


def TriLower(input_vertex: vertex_constructor_param_types, k: int, label: Optional[str]=None) -> Vertex:
    return Vertex(context.jvm_view().TriLowerVertex, label, cast_to_vertex(input_vertex), cast_to_integer(k))


def TriUpper(input_vertex: vertex_constructor_param_types, k: int, label: Optional[str]=None) -> Vertex:
    return Vertex(context.jvm_view().TriUpperVertex, label, cast_to_vertex(input_vertex), cast_to_integer(k))


def TrianglePart(input_vertex: vertex_constructor_param_types, upper_part: bool, label: Optional[str]=None) -> Vertex:
    return Vertex(context.jvm_view().TrianglePartVertex, label, cast_to_vertex(input_vertex), cast_to_boolean(upper_part))


def Where(predicate: vertex_constructor_param_types, thn: vertex_constructor_param_types, els: vertex_constructor_param_types, label: Optional[str]=None) -> Vertex:
    return Vertex(context.jvm_view().WhereVertex, label, cast_to_boolean_vertex(predicate), cast_to_vertex(thn), cast_to_vertex(els))


def BooleanProxy(shape: Collection[int], label: str) -> Vertex:
    return Boolean(context.jvm_view().BooleanProxyVertex, label, cast_to_long_array(shape), _VertexLabel(label))


def CastNumberToBoolean(input_vertex: vertex_constructor_param_types, label: Optional[str]=None) -> Vertex:
    return Boolean(context.jvm_view().CastNumberToBooleanVertex, label, cast_to_vertex(input_vertex))


def CastToBoolean(input_vertex: vertex_constructor_param_types, label: Optional[str]=None) -> Vertex:
    return Boolean(context.jvm_view().CastToBooleanVertex, label, cast_to_boolean_vertex(input_vertex))


def ConstantBoolean(constant: tensor_arg_types, label: Optional[str]=None) -> Vertex:
    return Boolean(context.jvm_view().ConstantBooleanVertex, label, cast_to_boolean_tensor(constant))


def AndBinary(a: vertex_constructor_param_types, b: vertex_constructor_param_types, label: Optional[str]=None) -> Vertex:
    return Boolean(context.jvm_view().AndBinaryVertex, label, cast_to_boolean_vertex(a), cast_to_boolean_vertex(b))


def OrBinary(a: vertex_constructor_param_types, b: vertex_constructor_param_types, label: Optional[str]=None) -> Vertex:
    return Boolean(context.jvm_view().OrBinaryVertex, label, cast_to_boolean_vertex(a), cast_to_boolean_vertex(b))


def XorBinary(a: vertex_constructor_param_types, b: vertex_constructor_param_types, label: Optional[str]=None) -> Vertex:
    return Boolean(context.jvm_view().XorBinaryVertex, label, cast_to_boolean_vertex(a), cast_to_boolean_vertex(b))


def Equals(a: vertex_constructor_param_types, b: vertex_constructor_param_types, label: Optional[str]=None) -> Vertex:
    return Boolean(context.jvm_view().EqualsVertex, label, cast_to_vertex(a), cast_to_vertex(b))


def GreaterThanOrEqual(a: vertex_constructor_param_types, b: vertex_constructor_param_types, label: Optional[str]=None) -> Vertex:
    return Boolean(context.jvm_view().GreaterThanOrEqualVertex, label, cast_to_vertex(a), cast_to_vertex(b))


def GreaterThan(a: vertex_constructor_param_types, b: vertex_constructor_param_types, label: Optional[str]=None) -> Vertex:
    return Boolean(context.jvm_view().GreaterThanVertex, label, cast_to_vertex(a), cast_to_vertex(b))


def LessThanOrEqual(a: vertex_constructor_param_types, b: vertex_constructor_param_types, label: Optional[str]=None) -> Vertex:
    return Boolean(context.jvm_view().LessThanOrEqualVertex, label, cast_to_vertex(a), cast_to_vertex(b))


def LessThan(a: vertex_constructor_param_types, b: vertex_constructor_param_types, label: Optional[str]=None) -> Vertex:
    return Boolean(context.jvm_view().LessThanVertex, label, cast_to_vertex(a), cast_to_vertex(b))


def NotEquals(a: vertex_constructor_param_types, b: vertex_constructor_param_types, label: Optional[str]=None) -> Vertex:
    return Boolean(context.jvm_view().NotEqualsVertex, label, cast_to_vertex(a), cast_to_vertex(b))


def NumericalEquals(a: vertex_constructor_param_types, b: vertex_constructor_param_types, epsilon: float, label: Optional[str]=None) -> Vertex:
    return Boolean(context.jvm_view().NumericalEqualsVertex, label, cast_to_vertex(a), cast_to_vertex(b), cast_to_double(epsilon))


def BooleanConcatenation(dimension: int, input: Collection[Vertex], label: Optional[str]=None) -> Vertex:
    return Boolean(context.jvm_view().BooleanConcatenationVertex, label, cast_to_integer(dimension), cast_to_vertex_array(input))


def BooleanToDoubleMask(input_vertex: vertex_constructor_param_types, label: Optional[str]=None) -> Vertex:
    return Double(context.jvm_view().BooleanToDoubleMaskVertex, label, cast_to_boolean_vertex(input_vertex))


def BooleanToIntegerMask(input_vertex: vertex_constructor_param_types, label: Optional[str]=None) -> Vertex:
    return Integer(context.jvm_view().BooleanToIntegerMaskVertex, label, cast_to_boolean_vertex(input_vertex))


def AllFalse(input_vertex: vertex_constructor_param_types, label: Optional[str]=None) -> Vertex:
    return Boolean(context.jvm_view().AllFalseVertex, label, cast_to_boolean_vertex(input_vertex))


def AllTrue(input_vertex: vertex_constructor_param_types, label: Optional[str]=None) -> Vertex:
    return Boolean(context.jvm_view().AllTrueVertex, label, cast_to_boolean_vertex(input_vertex))


def AnyFalse(input_vertex: vertex_constructor_param_types, label: Optional[str]=None) -> Vertex:
    return Boolean(context.jvm_view().AnyFalseVertex, label, cast_to_boolean_vertex(input_vertex))


def AnyTrue(input_vertex: vertex_constructor_param_types, label: Optional[str]=None) -> Vertex:
    return Boolean(context.jvm_view().AnyTrueVertex, label, cast_to_boolean_vertex(input_vertex))


def IsFinite(input_vertex: vertex_constructor_param_types, label: Optional[str]=None) -> Vertex:
    return Boolean(context.jvm_view().IsFiniteVertex, label, cast_to_vertex(input_vertex))


def IsInfinite(input_vertex: vertex_constructor_param_types, label: Optional[str]=None) -> Vertex:
    return Boolean(context.jvm_view().IsInfiniteVertex, label, cast_to_vertex(input_vertex))


def IsNaN(input_vertex: vertex_constructor_param_types, label: Optional[str]=None) -> Vertex:
    return Boolean(context.jvm_view().IsNaNVertex, label, cast_to_vertex(input_vertex))


def IsNegativeInfinity(input_vertex: vertex_constructor_param_types, label: Optional[str]=None) -> Vertex:
    return Boolean(context.jvm_view().IsNegativeInfinityVertex, label, cast_to_vertex(input_vertex))


def IsPositiveInfinity(input_vertex: vertex_constructor_param_types, label: Optional[str]=None) -> Vertex:
    return Boolean(context.jvm_view().IsPositiveInfinityVertex, label, cast_to_vertex(input_vertex))


def NotBinary(a: vertex_constructor_param_types, label: Optional[str]=None) -> Vertex:
    return Boolean(context.jvm_view().NotBinaryVertex, label, cast_to_boolean_vertex(a))


def NotNaN(input_vertex: vertex_constructor_param_types, label: Optional[str]=None) -> Vertex:
    return Boolean(context.jvm_view().NotNaNVertex, label, cast_to_vertex(input_vertex))


def Bernoulli(prob_true: vertex_constructor_param_types, label: Optional[str]=None) -> Vertex:
    """
    One to one constructor for mapping some shape of probTrue to
    a matching shaped Bernoulli.
    
    :param prob_true: probTrue with same shape as desired Bernoulli tensor or scalar
    """
    return Boolean(context.jvm_view().BernoulliVertex, label, cast_to_double_vertex(prob_true))


def Print(parent: vertex_constructor_param_types, message: str, print_data: bool, label: Optional[str]=None) -> Vertex:
    return Vertex(context.jvm_view().PrintVertex, label, cast_to_vertex(parent), message, cast_to_boolean(print_data))


def CastNumberToInteger(input_vertex: vertex_constructor_param_types, label: Optional[str]=None) -> Vertex:
    return Integer(context.jvm_view().CastNumberToIntegerVertex, label, cast_to_vertex(input_vertex))


def ConstantInteger(constant: tensor_arg_types, label: Optional[str]=None) -> Vertex:
    return Integer(context.jvm_view().ConstantIntegerVertex, label, cast_to_integer_tensor(constant))


def IntegerProxy(shape: Collection[int], label: str) -> Vertex:
    return Integer(context.jvm_view().IntegerProxyVertex, label, cast_to_long_array(shape), _VertexLabel(label))


def IntegerConcatenation(dimension: int, input: Collection[Vertex], label: Optional[str]=None) -> Vertex:
    return Integer(context.jvm_view().IntegerConcatenationVertex, label, cast_to_integer(dimension), cast_to_vertex_array(input))


def ArgMax(input_vertex: vertex_constructor_param_types, axis: int, label: Optional[str]=None) -> Vertex:
    return Integer(context.jvm_view().ArgMaxVertex, label, cast_to_vertex(input_vertex), cast_to_integer(axis))


def ArgMin(input_vertex: vertex_constructor_param_types, axis: int, label: Optional[str]=None) -> Vertex:
    return Integer(context.jvm_view().ArgMinVertex, label, cast_to_vertex(input_vertex), cast_to_integer(axis))


def NaNArgMax(input_vertex: vertex_constructor_param_types, axis: int, label: Optional[str]=None) -> Vertex:
    return Integer(context.jvm_view().NaNArgMaxVertex, label, cast_to_vertex(input_vertex), cast_to_integer(axis))


def NaNArgMin(input_vertex: vertex_constructor_param_types, axis: int, label: Optional[str]=None) -> Vertex:
    return Integer(context.jvm_view().NaNArgMinVertex, label, cast_to_vertex(input_vertex), cast_to_integer(axis))


def Binomial(p: vertex_constructor_param_types, n: vertex_constructor_param_types, label: Optional[str]=None) -> Vertex:
    return Integer(context.jvm_view().BinomialVertex, label, cast_to_double_vertex(p), cast_to_integer_vertex(n))


def Geometric(p: vertex_constructor_param_types, label: Optional[str]=None) -> Vertex:
    return Integer(context.jvm_view().GeometricVertex, label, cast_to_double_vertex(p))


def Multinomial(n: vertex_constructor_param_types, p: vertex_constructor_param_types, label: Optional[str]=None) -> Vertex:
    return Integer(context.jvm_view().MultinomialVertex, label, cast_to_integer_vertex(n), cast_to_double_vertex(p))


def Poisson(mu: vertex_constructor_param_types, label: Optional[str]=None) -> Vertex:
    """
    One to one constructor for mapping some shape of mu to
    a matching shaped Poisson.
    
    :param mu: mu with same shape as desired Poisson tensor or scalar
    """
    return Integer(context.jvm_view().PoissonVertex, label, cast_to_double_vertex(mu))


def UniformInt(min: vertex_constructor_param_types, max: vertex_constructor_param_types, label: Optional[str]=None) -> Vertex:
    return Integer(context.jvm_view().UniformIntVertex, label, cast_to_integer_vertex(min), cast_to_integer_vertex(max))


def Mod(left: vertex_constructor_param_types, right: vertex_constructor_param_types, label: Optional[str]=None) -> Vertex:
    return Vertex(context.jvm_view().ModVertex, label, cast_to_vertex(left), cast_to_vertex(right))


def CastNumberToDouble(input_vertex: vertex_constructor_param_types, label: Optional[str]=None) -> Vertex:
    return Double(context.jvm_view().CastNumberToDoubleVertex, label, cast_to_vertex(input_vertex))


def ConstantDouble(constant: tensor_arg_types, label: Optional[str]=None) -> Vertex:
    return Double(context.jvm_view().ConstantDoubleVertex, label, cast_to_double_tensor(constant))


def DoubleProxy(shape: Collection[int], label: str) -> Vertex:
    return Double(context.jvm_view().DoubleProxyVertex, label, cast_to_long_array(shape), _VertexLabel(label))


def Concatenation(dimension: int, operands: Collection[Vertex], label: Optional[str]=None) -> Vertex:
    """
    A vertex that can concatenate any amount of vertices along a given dimension.
    
    :param dimension: the dimension to concatenate on. This is the only dimension in which sizes may be different. Negative
                  dimension indexing is not supported.
    :param operands: the operands vertices to concatenate
    """
    return Double(context.jvm_view().ConcatenationVertex, label, cast_to_integer(dimension), cast_to_vertex_array(operands))


def Beta(alpha: vertex_constructor_param_types, beta: vertex_constructor_param_types, label: Optional[str]=None) -> Vertex:
    """
    One to one constructor for mapping some tensorShape of alpha and beta to
    a matching tensorShaped Beta.
    
    :param alpha: the alpha of the Beta with either the same tensorShape as specified for this vertex or a scalar
    :param beta: the beta of the Beta with either the same tensorShape as specified for this vertex or a scalar
    """
    return Double(context.jvm_view().BetaVertex, label, cast_to_double_vertex(alpha), cast_to_double_vertex(beta))


def Cauchy(location: vertex_constructor_param_types, scale: vertex_constructor_param_types, label: Optional[str]=None) -> Vertex:
    return Double(context.jvm_view().CauchyVertex, label, cast_to_double_vertex(location), cast_to_double_vertex(scale))


def ChiSquared(k: vertex_constructor_param_types, label: Optional[str]=None) -> Vertex:
    """
    One to one constructor for mapping some shape of k to
    a matching shaped ChiSquared.
    
    :param k: the number of degrees of freedom
    """
    return Double(context.jvm_view().ChiSquaredVertex, label, cast_to_integer_vertex(k))


def Dirichlet(concentration: vertex_constructor_param_types, label: Optional[str]=None) -> Vertex:
    """
    Matches a vector of concentration values to a Dirichlet distribution
    
    :param concentration: the concentration values of the dirichlet
    """
    return Double(context.jvm_view().DirichletVertex, label, cast_to_double_vertex(concentration))


def Exponential(rate: vertex_constructor_param_types, label: Optional[str]=None) -> Vertex:
    """
    One to one constructor for mapping some shape of rate to matching shaped exponential.
    
    :param rate: the rate of the Exponential with either the same shape as specified for this vertex or scalar
    """
    return Double(context.jvm_view().ExponentialVertex, label, cast_to_double_vertex(rate))


def Gamma(theta: vertex_constructor_param_types, k: vertex_constructor_param_types, label: Optional[str]=None) -> Vertex:
    """
    One to one constructor for mapping some shape of theta and k to matching shaped gamma.
    
    :param theta: the theta (scale) of the Gamma with either the same shape as specified for this vertex
    :param k: the k (shape) of the Gamma with either the same shape as specified for this vertex
    """
    return Double(context.jvm_view().GammaVertex, label, cast_to_double_vertex(theta), cast_to_double_vertex(k))


def Gaussian(mu: vertex_constructor_param_types, sigma: vertex_constructor_param_types, label: Optional[str]=None) -> Vertex:
    return Double(context.jvm_view().GaussianVertex, label, cast_to_double_vertex(mu), cast_to_double_vertex(sigma))


def HalfCauchy(scale: vertex_constructor_param_types, label: Optional[str]=None) -> Vertex:
    return Double(context.jvm_view().HalfCauchyVertex, label, cast_to_double_vertex(scale))


def HalfGaussian(sigma: vertex_constructor_param_types, label: Optional[str]=None) -> Vertex:
    return Double(context.jvm_view().HalfGaussianVertex, label, cast_to_double_vertex(sigma))


def InverseGamma(alpha: vertex_constructor_param_types, beta: vertex_constructor_param_types, label: Optional[str]=None) -> Vertex:
    """
    One to one constructor for mapping some shape of alpha and beta to
    alpha matching shaped Inverse Gamma.
    
    :param alpha: the alpha of the Inverse Gamma with either the same shape as specified for this vertex or alpha scalar
    :param beta: the beta of the Inverse Gamma with either the same shape as specified for this vertex or alpha scalar
    """
    return Double(context.jvm_view().InverseGammaVertex, label, cast_to_double_vertex(alpha), cast_to_double_vertex(beta))


def KDE(samples: tensor_arg_types, bandwidth: float, label: Optional[str]=None) -> Vertex:
    return Double(context.jvm_view().KDEVertex, label, cast_to_double_tensor(samples), cast_to_double(bandwidth))


def Laplace(mu: vertex_constructor_param_types, beta: vertex_constructor_param_types, label: Optional[str]=None) -> Vertex:
    """
    One to one constructor for mapping some shape of mu and sigma to
    a matching shaped Laplace.
    
    :param mu: the mu of the Laplace with either the same shape as specified for this vertex or a scalar
    :param beta: the beta of the Laplace with either the same shape as specified for this vertex or a scalar
    """
    return Double(context.jvm_view().LaplaceVertex, label, cast_to_double_vertex(mu), cast_to_double_vertex(beta))


def LogNormal(mu: vertex_constructor_param_types, sigma: vertex_constructor_param_types, label: Optional[str]=None) -> Vertex:
    return Double(context.jvm_view().LogNormalVertex, label, cast_to_double_vertex(mu), cast_to_double_vertex(sigma))


def Logistic(mu: vertex_constructor_param_types, s: vertex_constructor_param_types, label: Optional[str]=None) -> Vertex:
    return Double(context.jvm_view().LogisticVertex, label, cast_to_double_vertex(mu), cast_to_double_vertex(s))


def MultivariateGaussian(mu: vertex_constructor_param_types, covariance: vertex_constructor_param_types, label: Optional[str]=None) -> Vertex:
    """
    Matches a mu and full covariance matrix of some shape to a Multivariate Gaussian distribution. Mu should
    be shape (batchShape, N) where N is the number of dimensions and batchShape can be any shape that is broadcastable
    with the covariance batchShape if it is also batched. The covariance matrix should be shape (batchShape, N, N) where
    the batchShape must be broadcastable with the batchShape of mu. Only the lower triangle of the covariance matrix
    is used due to it being assumed to be a symmetric matrix. The upper triangle will be ignored.
    
    :param mu: the mu of the Multivariate Gaussian
    :param covariance: the covariance matrix of the Multivariate Gaussian
    """
    return Double(context.jvm_view().MultivariateGaussianVertex, label, cast_to_double_vertex(mu), cast_to_double_vertex(covariance))


def Pareto(location: vertex_constructor_param_types, scale: vertex_constructor_param_types, label: Optional[str]=None) -> Vertex:
    return Double(context.jvm_view().ParetoVertex, label, cast_to_double_vertex(location), cast_to_double_vertex(scale))


def StudentT(v: vertex_constructor_param_types, label: Optional[str]=None) -> Vertex:
    return Double(context.jvm_view().StudentTVertex, label, cast_to_integer_vertex(v))


def Triangular(x_min: vertex_constructor_param_types, x_max: vertex_constructor_param_types, c: vertex_constructor_param_types, label: Optional[str]=None) -> Vertex:
    """
    One to one constructor for mapping some shape of xMin, xMax and c to a matching shaped triangular.
    
    :param x_min: the xMin of the Triangular with either the same shape as specified for this vertex or a scalar
    :param x_max: the xMax of the Triangular with either the same shape as specified for this vertex or a scalar
    :param c: the c of the Triangular with either the same shape as specified for this vertex or a scalar
    """
    return Double(context.jvm_view().TriangularVertex, label, cast_to_double_vertex(x_min), cast_to_double_vertex(x_max), cast_to_double_vertex(c))


def Uniform(x_min: vertex_constructor_param_types, x_max: vertex_constructor_param_types, label: Optional[str]=None) -> Vertex:
    """
    One to one constructor for mapping some shape of mu and sigma to
    a matching shaped Uniform Vertex
    
    :param x_min: the inclusive lower bound of the Uniform with either the same shape as specified for this vertex or a scalar
    :param x_max: the exclusive upper bound of the Uniform with either the same shape as specified for this vertex or a scalar
    """
    return Double(context.jvm_view().UniformVertex, label, cast_to_double_vertex(x_min), cast_to_double_vertex(x_max))


def ArcTan2(x: vertex_constructor_param_types, y: vertex_constructor_param_types, label: Optional[str]=None) -> Vertex:
    """
    Calculates the signed angle, in radians, between the positive x-axis and a ray to the point (x, y) from the origin
    
    :param x: x coordinate
    :param y: y coordinate
    """
    return Vertex(context.jvm_view().ArcTan2Vertex, label, cast_to_vertex(x), cast_to_vertex(y))


def LogAddExp2(left: vertex_constructor_param_types, right: vertex_constructor_param_types, label: Optional[str]=None) -> Vertex:
    return Vertex(context.jvm_view().LogAddExp2Vertex, label, cast_to_vertex(left), cast_to_vertex(right))


def LogAddExp(left: vertex_constructor_param_types, right: vertex_constructor_param_types, label: Optional[str]=None) -> Vertex:
    return Vertex(context.jvm_view().LogAddExpVertex, label, cast_to_vertex(left), cast_to_vertex(right))


def SafeLogTimes(x: vertex_constructor_param_types, y: vertex_constructor_param_types, label: Optional[str]=None) -> Vertex:
    return Vertex(context.jvm_view().SafeLogTimesVertex, label, cast_to_vertex(x), cast_to_vertex(y))


def ArcCos(input_vertex: vertex_constructor_param_types, label: Optional[str]=None) -> Vertex:
    """
    Takes the inverse cosine of a vertex, Arccos(vertex)
    
    :param input_vertex: the vertex
    """
    return Vertex(context.jvm_view().ArcCosVertex, label, cast_to_vertex(input_vertex))


def ArcCosh(input_vertex: vertex_constructor_param_types, label: Optional[str]=None) -> Vertex:
    """
    
    
    :param input_vertex: the vertex
    """
    return Vertex(context.jvm_view().ArcCoshVertex, label, cast_to_vertex(input_vertex))


def ArcSin(input_vertex: vertex_constructor_param_types, label: Optional[str]=None) -> Vertex:
    """
    Takes the inverse sin of a vertex, Arcsin(vertex)
    
    :param input_vertex: the vertex
    """
    return Vertex(context.jvm_view().ArcSinVertex, label, cast_to_vertex(input_vertex))


def ArcSinh(input_vertex: vertex_constructor_param_types, label: Optional[str]=None) -> Vertex:
    """
    
    
    :param input_vertex: the vertex
    """
    return Vertex(context.jvm_view().ArcSinhVertex, label, cast_to_vertex(input_vertex))


def ArcTan(input_vertex: vertex_constructor_param_types, label: Optional[str]=None) -> Vertex:
    """
    Takes the inverse tan of a vertex, Arctan(vertex)
    
    :param input_vertex: the vertex
    """
    return Vertex(context.jvm_view().ArcTanVertex, label, cast_to_vertex(input_vertex))


def ArcTanh(input_vertex: vertex_constructor_param_types, label: Optional[str]=None) -> Vertex:
    """
    
    
    :param input_vertex: the vertex
    """
    return Vertex(context.jvm_view().ArcTanhVertex, label, cast_to_vertex(input_vertex))


def Ceil(input_vertex: vertex_constructor_param_types, label: Optional[str]=None) -> Vertex:
    """
    Applies the Ceiling operator to a vertex.
    This maps a vertex to the smallest integer greater than or equal to its value
    
    :param input_vertex: the vertex to be ceil'd
    """
    return Vertex(context.jvm_view().CeilVertex, label, cast_to_vertex(input_vertex))


def CholeskyDecomposition(input_vertex: vertex_constructor_param_types, label: Optional[str]=None) -> Vertex:
    """
    
    
    :param input_vertex: the vertex
    """
    return Vertex(context.jvm_view().CholeskyDecompositionVertex, label, cast_to_vertex(input_vertex))


def CholeskyInverse(input_vertex: vertex_constructor_param_types, label: Optional[str]=None) -> Vertex:
    """
    
    
    :param input_vertex: the vertex
    """
    return Vertex(context.jvm_view().CholeskyInverseVertex, label, cast_to_vertex(input_vertex))


def Cos(input_vertex: vertex_constructor_param_types, label: Optional[str]=None) -> Vertex:
    """
    Takes the cosine of a vertex, Cos(vertex)
    
    :param input_vertex: the vertex
    """
    return Vertex(context.jvm_view().CosVertex, label, cast_to_vertex(input_vertex))


def Cosh(input_vertex: vertex_constructor_param_types, label: Optional[str]=None) -> Vertex:
    """
    
    
    :param input_vertex: the vertex
    """
    return Vertex(context.jvm_view().CoshVertex, label, cast_to_vertex(input_vertex))


def Digamma(input_vertex: vertex_constructor_param_types, label: Optional[str]=None) -> Vertex:
    """
    
    
    :param input_vertex: the vertex
    """
    return Vertex(context.jvm_view().DigammaVertex, label, cast_to_vertex(input_vertex))


def Exp2(input_vertex: vertex_constructor_param_types, label: Optional[str]=None) -> Vertex:
    """
    
    
    :param input_vertex: the vertex
    """
    return Vertex(context.jvm_view().Exp2Vertex, label, cast_to_vertex(input_vertex))


def ExpM1(input_vertex: vertex_constructor_param_types, label: Optional[str]=None) -> Vertex:
    """
    
    
    :param input_vertex: the vertex
    """
    return Vertex(context.jvm_view().ExpM1Vertex, label, cast_to_vertex(input_vertex))


def Exp(input_vertex: vertex_constructor_param_types, label: Optional[str]=None) -> Vertex:
    """
    Calculates the exponential of an input vertex
    
    :param input_vertex: the vertex
    """
    return Vertex(context.jvm_view().ExpVertex, label, cast_to_vertex(input_vertex))


def Floor(input_vertex: vertex_constructor_param_types, label: Optional[str]=None) -> Vertex:
    """
    Applies the Floor operator to a vertex.
    This maps a vertex to the biggest integer less than or equal to its value
    
    :param input_vertex: the vertex to be floor'd
    """
    return Vertex(context.jvm_view().FloorVertex, label, cast_to_vertex(input_vertex))


def Log10(input_vertex: vertex_constructor_param_types, label: Optional[str]=None) -> Vertex:
    """
    
    
    :param input_vertex: the vertex
    """
    return Vertex(context.jvm_view().Log10Vertex, label, cast_to_vertex(input_vertex))


def Log1p(input_vertex: vertex_constructor_param_types, label: Optional[str]=None) -> Vertex:
    """
    
    
    :param input_vertex: the vertex
    """
    return Vertex(context.jvm_view().Log1pVertex, label, cast_to_vertex(input_vertex))


def Log2(input_vertex: vertex_constructor_param_types, label: Optional[str]=None) -> Vertex:
    """
    
    
    :param input_vertex: the vertex
    """
    return Vertex(context.jvm_view().Log2Vertex, label, cast_to_vertex(input_vertex))


def LogGamma(input_vertex: vertex_constructor_param_types, label: Optional[str]=None) -> Vertex:
    """
    Returns the log of the gamma of the inputVertex
    
    :param input_vertex: the vertex
    """
    return Vertex(context.jvm_view().LogGammaVertex, label, cast_to_vertex(input_vertex))


def Log(input_vertex: vertex_constructor_param_types, label: Optional[str]=None) -> Vertex:
    """
    Returns the natural logarithm, base e, of a vertex
    
    :param input_vertex: the vertex
    """
    return Vertex(context.jvm_view().LogVertex, label, cast_to_vertex(input_vertex))


def MatrixDeterminant(vertex: vertex_constructor_param_types, label: Optional[str]=None) -> Vertex:
    return Vertex(context.jvm_view().MatrixDeterminantVertex, label, cast_to_vertex(vertex))


def MatrixInverse(input_vertex: vertex_constructor_param_types, label: Optional[str]=None) -> Vertex:
    return Vertex(context.jvm_view().MatrixInverseVertex, label, cast_to_vertex(input_vertex))


def Mean(input_vertex: vertex_constructor_param_types, over_dimensions: Collection[int], label: Optional[str]=None) -> Vertex:
    """
    Performs a sum across specified dimensions. Negative dimension indexing is not supported.
    
    :param input_vertex: the vertex to have its values summed
    :param over_dimensions: dimensions to sum over
    """
    return Vertex(context.jvm_view().MeanVertex, label, cast_to_vertex(input_vertex), cast_to_int_array(over_dimensions))


def ReplaceNaN(input_vertex: vertex_constructor_param_types, replace_with_value: float, label: Optional[str]=None) -> Vertex:
    return Vertex(context.jvm_view().ReplaceNaNVertex, label, cast_to_vertex(input_vertex), cast_to_double(replace_with_value))


def Round(input_vertex: vertex_constructor_param_types, label: Optional[str]=None) -> Vertex:
    """
    Applies the Rounding operator to a vertex.
    This maps a vertex to the nearest integer value
    
    :param input_vertex: the vertex to be rounded
    """
    return Vertex(context.jvm_view().RoundVertex, label, cast_to_vertex(input_vertex))


def Sigmoid(input_vertex: vertex_constructor_param_types, label: Optional[str]=None) -> Vertex:
    """
    Applies the sigmoid function to a vertex.
    The sigmoid function is a special case of the Logistic function.
    
    :param input_vertex: the vertex
    """
    return Vertex(context.jvm_view().SigmoidVertex, label, cast_to_vertex(input_vertex))


def Sin(input_vertex: vertex_constructor_param_types, label: Optional[str]=None) -> Vertex:
    """
    Takes the sine of a vertex. Sin(vertex).
    
    :param input_vertex: the vertex
    """
    return Vertex(context.jvm_view().SinVertex, label, cast_to_vertex(input_vertex))


def Sinh(input_vertex: vertex_constructor_param_types, label: Optional[str]=None) -> Vertex:
    """
    
    
    :param input_vertex: the vertex
    """
    return Vertex(context.jvm_view().SinhVertex, label, cast_to_vertex(input_vertex))


def StandardDeviation(input_vertex: vertex_constructor_param_types, label: Optional[str]=None) -> Vertex:
    """
    
    
    :param input_vertex: the vertex
    """
    return Vertex(context.jvm_view().StandardDeviationVertex, label, cast_to_vertex(input_vertex))


def Standardize(input_vertex: vertex_constructor_param_types, label: Optional[str]=None) -> Vertex:
    return Vertex(context.jvm_view().StandardizeVertex, label, cast_to_vertex(input_vertex))


def Tan(input_vertex: vertex_constructor_param_types, label: Optional[str]=None) -> Vertex:
    """
    Takes the tangent of a vertex. Tan(vertex).
    
    :param input_vertex: the vertex
    """
    return Vertex(context.jvm_view().TanVertex, label, cast_to_vertex(input_vertex))


def Tanh(input_vertex: vertex_constructor_param_types, label: Optional[str]=None) -> Vertex:
    """
    
    
    :param input_vertex: the vertex
    """
    return Vertex(context.jvm_view().TanhVertex, label, cast_to_vertex(input_vertex))


def Trigamma(input_vertex: vertex_constructor_param_types, label: Optional[str]=None) -> Vertex:
    """
    
    
    :param input_vertex: the vertex
    """
    return Vertex(context.jvm_view().TrigammaVertex, label, cast_to_vertex(input_vertex))


def Addition(left: vertex_constructor_param_types, right: vertex_constructor_param_types, label: Optional[str]=None) -> Vertex:
    """
    Adds one vertex to another
    
    :param left: a vertex to add
    :param right: a vertex to add
    """
    return Vertex(context.jvm_view().AdditionVertex, label, cast_to_vertex(left), cast_to_vertex(right))


def Difference(left: vertex_constructor_param_types, right: vertex_constructor_param_types, label: Optional[str]=None) -> Vertex:
    return Vertex(context.jvm_view().DifferenceVertex, label, cast_to_vertex(left), cast_to_vertex(right))


def Division(left: vertex_constructor_param_types, right: vertex_constructor_param_types, label: Optional[str]=None) -> Vertex:
    """
    Divides one vertex by another
    
    :param left: the vertex to be divided
    :param right: the vertex to divide
    """
    return Vertex(context.jvm_view().DivisionVertex, label, cast_to_vertex(left), cast_to_vertex(right))


def GreaterThanMask(left: vertex_constructor_param_types, right: vertex_constructor_param_types, label: Optional[str]=None) -> Vertex:
    return Vertex(context.jvm_view().GreaterThanMaskVertex, label, cast_to_vertex(left), cast_to_vertex(right))


def GreaterThanOrEqualToMask(left: vertex_constructor_param_types, right: vertex_constructor_param_types, label: Optional[str]=None) -> Vertex:
    return Vertex(context.jvm_view().GreaterThanOrEqualToMaskVertex, label, cast_to_vertex(left), cast_to_vertex(right))


def LessThanMask(left: vertex_constructor_param_types, right: vertex_constructor_param_types, label: Optional[str]=None) -> Vertex:
    return Vertex(context.jvm_view().LessThanMaskVertex, label, cast_to_vertex(left), cast_to_vertex(right))


def LessThanOrEqualToMask(left: vertex_constructor_param_types, right: vertex_constructor_param_types, label: Optional[str]=None) -> Vertex:
    return Vertex(context.jvm_view().LessThanOrEqualToMaskVertex, label, cast_to_vertex(left), cast_to_vertex(right))


def MatrixMultiplication(left: vertex_constructor_param_types, right: vertex_constructor_param_types, transpose_left: bool, transpose_right: bool, label: Optional[str]=None) -> Vertex:
    """
    Matrix multiplies one vertex by another. C = AB
    
    :param left: vertex A
    :param right: vertex B
    :param transpose_left: transpose the left operand before multiply
    :param transpose_right: transpose the right operand before multiply
    """
    return Vertex(context.jvm_view().MatrixMultiplicationVertex, label, cast_to_vertex(left), cast_to_vertex(right), cast_to_boolean(transpose_left), cast_to_boolean(transpose_right))


def Max(left: vertex_constructor_param_types, right: vertex_constructor_param_types, label: Optional[str]=None) -> Vertex:
    """
    Finds the minimum between two vertices
    
    :param left: one of the vertices to find the minimum of
    :param right: one of the vertices to find the minimum of
    """
    return Vertex(context.jvm_view().MaxVertex, label, cast_to_vertex(left), cast_to_vertex(right))


def Min(left: vertex_constructor_param_types, right: vertex_constructor_param_types, label: Optional[str]=None) -> Vertex:
    """
    Finds the minimum between two vertices
    
    :param left: one of the vertices to find the minimum of
    :param right: one of the vertices to find the minimum of
    """
    return Vertex(context.jvm_view().MinVertex, label, cast_to_vertex(left), cast_to_vertex(right))


def Multiplication(left: vertex_constructor_param_types, right: vertex_constructor_param_types, label: Optional[str]=None) -> Vertex:
    """
    Multiplies one vertex by another
    
    :param left: vertex to be multiplied
    :param right: vertex to be multiplied
    """
    return Vertex(context.jvm_view().MultiplicationVertex, label, cast_to_vertex(left), cast_to_vertex(right))


def Power(base: vertex_constructor_param_types, exponent: vertex_constructor_param_types, label: Optional[str]=None) -> Vertex:
    """
    Raises a vertex to the power of another
    
    :param base: the base vertex
    :param exponent: the exponent vertex
    """
    return Vertex(context.jvm_view().PowerVertex, label, cast_to_vertex(base), cast_to_vertex(exponent))


def TensorMultiplication(left: vertex_constructor_param_types, right: vertex_constructor_param_types, dims_left: Collection[int], dims_right: Collection[int], label: Optional[str]=None) -> Vertex:
    """
    Tensor multiplies one vertex by another. C = AB.
    
    :param left: the left vertex for operand
    :param right: the right vertex for operand
    :param dims_left: The dimensions of the left for multiplying. The left shape at these dimensions must align with the
                  shape of the corresponding right vertex at its specified dimensions.
    :param dims_right: The dimensions of the right for multiplying. The right shape at these dimensions must align with the
                  shape of the corresponding left vertex at its specified dimensions.
    """
    return Vertex(context.jvm_view().TensorMultiplicationVertex, label, cast_to_vertex(left), cast_to_vertex(right), cast_to_int_array(dims_left), cast_to_int_array(dims_right))


def SetWithMask(operand: vertex_constructor_param_types, mask: vertex_constructor_param_types, set_value: vertex_constructor_param_types, label: Optional[str]=None) -> Vertex:
    return Vertex(context.jvm_view().SetWithMaskVertex, label, cast_to_vertex(operand), cast_to_vertex(mask), cast_to_vertex(set_value))


def Abs(input_vertex: vertex_constructor_param_types, label: Optional[str]=None) -> Vertex:
    """
    Takes the absolute of a vertex
    
    :param input_vertex: the vertex
    """
    return Vertex(context.jvm_view().AbsVertex, label, cast_to_vertex(input_vertex))


def CumProd(input_vertex: vertex_constructor_param_types, requested_dimension: int, label: Optional[str]=None) -> Vertex:
    return Vertex(context.jvm_view().CumProdVertex, label, cast_to_vertex(input_vertex), cast_to_integer(requested_dimension))


def CumSum(input_vertex: vertex_constructor_param_types, requested_dimension: int, label: Optional[str]=None) -> Vertex:
    return Vertex(context.jvm_view().CumSumVertex, label, cast_to_vertex(input_vertex), cast_to_integer(requested_dimension))


def MaxUnary(input_vertex: vertex_constructor_param_types, label: Optional[str]=None) -> Vertex:
    return Vertex(context.jvm_view().MaxUnaryVertex, label, cast_to_vertex(input_vertex))


def MinUnary(input_vertex: vertex_constructor_param_types, label: Optional[str]=None) -> Vertex:
    return Vertex(context.jvm_view().MinUnaryVertex, label, cast_to_vertex(input_vertex))


def Product(input_vertex: vertex_constructor_param_types, over_dimensions: Collection[int], label: Optional[str]=None) -> Vertex:
    return Vertex(context.jvm_view().ProductVertex, label, cast_to_vertex(input_vertex), cast_to_int_array(over_dimensions))


def Sign(input_vertex: vertex_constructor_param_types, label: Optional[str]=None) -> Vertex:
    """
    Takes the sign of a vertex
    
    :param input_vertex: the vertex
    """
    return Vertex(context.jvm_view().SignVertex, label, cast_to_vertex(input_vertex))


def Sum(input_vertex: vertex_constructor_param_types, over_dimensions: Collection[int], label: Optional[str]=None) -> Vertex:
    """
    Performs a sum across specified dimensions. Negative dimension indexing is not supported.
    
    :param input_vertex: the vertex to have its values summed
    :param over_dimensions: dimensions to sum over
    """
    return Vertex(context.jvm_view().SumVertex, label, cast_to_vertex(input_vertex), cast_to_int_array(over_dimensions))


def Assert(predicate: vertex_constructor_param_types, error_message: str, label: Optional[str]=None) -> Vertex:
    """
    A vertex that asserts a {@link BooleanVertex} is all true on calculation.
    
    :param predicate: the predicate to evaluate
    :param error_message: a message to include in the {@link AssertionError}
    """
    return Boolean(context.jvm_view().AssertVertex, label, cast_to_boolean_vertex(predicate), error_message)
