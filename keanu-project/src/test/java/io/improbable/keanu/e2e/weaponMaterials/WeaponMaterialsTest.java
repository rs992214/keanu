package io.improbable.keanu.e2e.weaponMaterials;

import io.improbable.keanu.Keanu;
import io.improbable.keanu.algorithms.NetworkSamples;
import io.improbable.keanu.network.BayesianNetwork;
import io.improbable.keanu.network.KeanuProbabilisticModel;
import io.improbable.keanu.tensor.generic.GenericTensor;
import io.improbable.keanu.vertices.ConstantVertex;
import io.improbable.keanu.vertices.bool.BooleanVertex;
import io.improbable.keanu.vertices.bool.nonprobabilistic.operators.binary.compare.EqualsVertex;
import io.improbable.keanu.vertices.bool.probabilistic.BernoulliVertex;
import io.improbable.keanu.vertices.dbl.DoubleVertex;
import io.improbable.keanu.vertices.dbl.nonprobabilistic.DoubleCPTVertex;
import io.improbable.keanu.vertices.generic.nonprobabilistic.ConditionalProbabilityTable;
import io.improbable.keanu.vertices.generic.probabilistic.discrete.CategoricalVertex;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class WeaponMaterialsTest {

    public enum Weapon {
        POISON,
        SPANNER,
        CLUB,
        KNIFE
    }

    public enum Material {
        IRON,
        WOOD,
        STEAL,
        RUBBER,
        FUNNY_SMELL
    }

    public enum WeaponsAndMaterials {
        POISON,
        SPANNER,
        CLUB,
        KNIFE,
        IRON,
        WOOD,
        STEAL,
        RUBBER,
        FUNNY_SMELL
    }

    @Test
    public void testBooleanConditions() {
        CategoricalVertex<Weapon, GenericTensor<Weapon>> murderWeapon = getWeaponPrior();
        CategoricalVertex<Material, GenericTensor<Material>> weaponMaterial = getWeaponMaterialPrior();

        BooleanVertex isPoison = new EqualsVertex<>(murderWeapon, ConstantVertex.of(Weapon.POISON));
        BooleanVertex isSpanner = new EqualsVertex<>(murderWeapon, ConstantVertex.of(Weapon.SPANNER));
        BooleanVertex isClub = new EqualsVertex<>(murderWeapon, ConstantVertex.of(Weapon.CLUB));
        BooleanVertex isKnife = new EqualsVertex<>(murderWeapon, ConstantVertex.of(Weapon.KNIFE));
        BooleanVertex isIron = new EqualsVertex<>(weaponMaterial, ConstantVertex.of(Material.IRON));
        BooleanVertex isWood = new EqualsVertex<>(weaponMaterial, ConstantVertex.of(Material.WOOD));
        BooleanVertex isSteal = new EqualsVertex<>(weaponMaterial, ConstantVertex.of(Material.STEAL));
        BooleanVertex isRubber = new EqualsVertex<>(weaponMaterial, ConstantVertex.of(Material.RUBBER));
        BooleanVertex isFunnySmell = new EqualsVertex<>(weaponMaterial, ConstantVertex.of(Material.FUNNY_SMELL));

        DoubleCPTVertex cpt = ConditionalProbabilityTable.of(isPoison, isSpanner, isClub, isKnife, isIron, isWood, isSteal, isRubber, isFunnySmell)
            .when(true, false, false, false, false, false, false, false, true).then(1.0)
            .when(false, true, false, false, true, false, false, false, false).then(0.5)
            .when(false, true, false, false, false, false, true, false, false).then(0.5)
            .when(false, false, true, false, true, false, false, false, false).then(0.25)
            .when(false, false, true, false, false, true, false, false, false).then(0.25)
            .when(false, false, true, false, false, false, true, false, false).then(0.25)
            .when(false, false, true, false, false, false, false, true, false).then(0.25)
            .when(false, false, false, true, true, false, false, false, false).then(0.3333333333333333)
            .when(false, false, false, true, false, false, true, false, false).then(0.3333333333333333)
            .when(false, false, false, true, false, false, false, true, false).then(0.3333333333333333)
            .orDefault(0.0);

        new BernoulliVertex(cpt).observe(true);

        murderWeapon.observe(GenericTensor.scalar(Weapon.CLUB));

        BayesianNetwork bnet = new BayesianNetwork(cpt.getConnectedGraph());
        bnet.probeForNonZeroProbability(10000);
        KeanuProbabilisticModel model = new KeanuProbabilisticModel(bnet);

        NetworkSamples samples = Keanu.Sampling.MetropolisHastings.withDefaultConfig().generatePosteriorSamples(
            model,
            Arrays.asList(murderWeapon, weaponMaterial)
        ).generate(11000).drop(1000).downSample(2);

        printWeaponSamples(samples, murderWeapon);
        printMaterialSamples(samples, weaponMaterial);
    }

    @Test
    public void testEnumConditions() {
        Map<WeaponsAndMaterials, DoubleVertex> murderWeaponPrior = new HashMap<>();
        murderWeaponPrior.put(WeaponsAndMaterials.POISON, ConstantVertex.of(0.25));
        murderWeaponPrior.put(WeaponsAndMaterials.SPANNER, ConstantVertex.of(0.25));
        murderWeaponPrior.put(WeaponsAndMaterials.CLUB, ConstantVertex.of(0.25));
        murderWeaponPrior.put(WeaponsAndMaterials.KNIFE, ConstantVertex.of(0.25));
        CategoricalVertex<WeaponsAndMaterials, GenericTensor<WeaponsAndMaterials>> murderWeapon = new CategoricalVertex<>(murderWeaponPrior);

        Map<WeaponsAndMaterials, DoubleVertex> weaponMaterialPrior = new HashMap<>();
        weaponMaterialPrior.put(WeaponsAndMaterials.IRON, ConstantVertex.of(0.2));
        weaponMaterialPrior.put(WeaponsAndMaterials.WOOD, ConstantVertex.of(0.2));
        weaponMaterialPrior.put(WeaponsAndMaterials.STEAL, ConstantVertex.of(0.2));
        weaponMaterialPrior.put(WeaponsAndMaterials.RUBBER, ConstantVertex.of(0.2));
        weaponMaterialPrior.put(WeaponsAndMaterials.FUNNY_SMELL, ConstantVertex.of(0.2));
        CategoricalVertex<WeaponsAndMaterials, GenericTensor<WeaponsAndMaterials>> weaponMaterial = new CategoricalVertex<>(weaponMaterialPrior);

        DoubleCPTVertex cpt = ConditionalProbabilityTable.of(murderWeapon, weaponMaterial)
            .when(WeaponsAndMaterials.POISON, WeaponsAndMaterials.FUNNY_SMELL).then(1.0)
            .when(WeaponsAndMaterials.SPANNER, WeaponsAndMaterials.IRON).then(0.5)
            .when(WeaponsAndMaterials.SPANNER, WeaponsAndMaterials.STEAL).then(0.5)
            .when(WeaponsAndMaterials.CLUB, WeaponsAndMaterials.IRON).then(0.25)
            .when(WeaponsAndMaterials.CLUB, WeaponsAndMaterials.WOOD).then(0.25)
            .when(WeaponsAndMaterials.CLUB, WeaponsAndMaterials.STEAL).then(0.25)
            .when(WeaponsAndMaterials.CLUB, WeaponsAndMaterials.RUBBER).then(0.25)
            .when(WeaponsAndMaterials.KNIFE, WeaponsAndMaterials.IRON).then(0.3333333333333333)
            .when(WeaponsAndMaterials.KNIFE, WeaponsAndMaterials.STEAL).then(0.3333333333333333)
            .when(WeaponsAndMaterials.KNIFE, WeaponsAndMaterials.RUBBER).then(0.3333333333333333)
            .orDefault(0.0);

        new BernoulliVertex(cpt).observe(true);

        murderWeapon.observe(GenericTensor.scalar(WeaponsAndMaterials.POISON));

        BayesianNetwork bnet = new BayesianNetwork(cpt.getConnectedGraph());
        bnet.probeForNonZeroProbability(10000);
        KeanuProbabilisticModel model = new KeanuProbabilisticModel(bnet);

        NetworkSamples samples = Keanu.Sampling.MetropolisHastings.withDefaultConfig().generatePosteriorSamples(
            model,
            Arrays.asList(murderWeapon, weaponMaterial)
        ).generate(11000).drop(1000).downSample(2);

        printSamples(samples, murderWeapon);
        printSamples(samples, weaponMaterial);
    }

    private CategoricalVertex<Weapon, GenericTensor<Weapon>> getWeaponPrior() {
        Map<Weapon, DoubleVertex> weaponPrior = new HashMap<>();
        weaponPrior.put(Weapon.POISON, ConstantVertex.of(0.25));
        weaponPrior.put(Weapon.SPANNER, ConstantVertex.of(0.25));
        weaponPrior.put(Weapon.CLUB, ConstantVertex.of(0.25));
        weaponPrior.put(Weapon.KNIFE, ConstantVertex.of(0.25));
        return new CategoricalVertex<>(weaponPrior);
    }

    private CategoricalVertex<Material, GenericTensor<Material>> getWeaponMaterialPrior() {
        Map<Material, DoubleVertex> weaponMaterialPrior = new HashMap<>();
        weaponMaterialPrior.put(Material.IRON, ConstantVertex.of(0.2));
        weaponMaterialPrior.put(Material.WOOD, ConstantVertex.of(0.2));
        weaponMaterialPrior.put(Material.STEAL, ConstantVertex.of(0.2));
        weaponMaterialPrior.put(Material.RUBBER, ConstantVertex.of(0.2));
        weaponMaterialPrior.put(Material.FUNNY_SMELL, ConstantVertex.of(0.2));
        return new CategoricalVertex<>(weaponMaterialPrior);
    }

    private void printWeaponSamples(NetworkSamples samples,
                                    CategoricalVertex<Weapon, GenericTensor<Weapon>> weapon) {

        List<Weapon> weaponSamples = samples.get(weapon).asList().stream()
            .map(s -> s.scalar()).collect(Collectors.toList());

        Map<Weapon, Long> materialSampleCounts = weaponSamples.stream()
            .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        for (Map.Entry<Weapon, Long> entry : materialSampleCounts.entrySet()) {
            System.out.println(entry.getKey().toString() + ": " + entry.getValue());
        }
    }

    private void printMaterialSamples(NetworkSamples samples,
                                      CategoricalVertex<Material, GenericTensor<Material>> material) {

        List<Material> materialSamples = samples.get(material).asList().stream()
            .map(s -> s.scalar()).collect(Collectors.toList());

        Map<Material, Long> materialSampleCounts = materialSamples.stream()
            .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        for (Map.Entry<Material, Long> entry : materialSampleCounts.entrySet()) {
            System.out.println(entry.getKey().toString() + ": " + entry.getValue());
        }
    }

    private void printSamples(NetworkSamples samples,
                              CategoricalVertex<WeaponsAndMaterials, GenericTensor<WeaponsAndMaterials>> vertex) {

        List<WeaponsAndMaterials> vertexSamples = samples.get(vertex).asList().stream()
            .map(s -> s.scalar()).collect(Collectors.toList());

        Map<WeaponsAndMaterials, Long> vertexSampleCounts = vertexSamples.stream()
            .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

        for (Map.Entry<WeaponsAndMaterials, Long> entry : vertexSampleCounts.entrySet()) {
            System.out.println(entry.getKey().toString() + ": " + entry.getValue());
        }
    }
}
