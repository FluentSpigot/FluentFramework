package io.github.jwdeveloper.ff.extension.resourcepack.tools;

public  class ResourcepackTools
{
    public static void copyResourcepackToProject()
    {

    }


    public static void copyResourcepackFromProject()
    {

    }

    public static void generateResourcepackModelsAsClass(String resourcePackModelInput,String outputNamespace)
    {
          new ItemModelsClassGenerator(resourcePackModelInput, outputNamespace).generate();
    }
}
