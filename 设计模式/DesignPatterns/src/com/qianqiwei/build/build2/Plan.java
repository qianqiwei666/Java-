package com.qianqiwei.build.build2;

public class Plan {
    private String name;
    private String type;

    private int height;
    private int width;


    public Plan(String name, String type, int height, int width) {
        this.name = name;
        this.type = type;
        this.height = height;
        this.width = width;
    }

    public Plan() {
    }

    @Override
    public String toString() {
        return "Plan{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", height=" + height +
                ", width=" + width +
                '}';
    }

    public static class PlanBuilder {
        private Plan plan = new Plan();

        //构建基本参数
        public PlanBuilder buildBasic(String name, String type) {
            plan.name = name;
            plan.type = type;
            return this;
        }

        public PlanBuilder buildBasic01(int width, int height) {
            plan.width = width;
            plan.height = height;
            return this;
        }

        public Plan build() {
            return plan;
        }
    }
}
