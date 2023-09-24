package android.gesture;

import java.util.ArrayList;

/* loaded from: classes.dex */
abstract class Learner {
    private final ArrayList<Instance> mInstances = new ArrayList<>();

    abstract ArrayList<Prediction> classify(int i, int i2, float[] fArr);

    Learner() {
    }

    void addInstance(Instance instance) {
        this.mInstances.add(instance);
    }

    ArrayList<Instance> getInstances() {
        return this.mInstances;
    }

    void removeInstance(long id) {
        ArrayList<Instance> instances = this.mInstances;
        int count = instances.size();
        for (int i = 0; i < count; i++) {
            Instance instance = instances.get(i);
            if (id == instance.f43id) {
                instances.remove(instance);
                return;
            }
        }
    }

    void removeInstances(String name) {
        ArrayList<Instance> toDelete = new ArrayList<>();
        ArrayList<Instance> instances = this.mInstances;
        int count = instances.size();
        for (int i = 0; i < count; i++) {
            Instance instance = instances.get(i);
            if ((instance.label == null && name == null) || (instance.label != null && instance.label.equals(name))) {
                toDelete.add(instance);
            }
        }
        instances.removeAll(toDelete);
    }
}
