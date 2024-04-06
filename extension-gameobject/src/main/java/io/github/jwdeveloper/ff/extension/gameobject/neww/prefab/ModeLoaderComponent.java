package io.github.jwdeveloper.ff.extension.gameobject.neww.prefab;

import io.github.jwdeveloper.dependance.injector.api.annotations.Injection;
import io.github.jwdeveloper.dependance.injector.api.enums.LifeTime;
import io.github.jwdeveloper.ff.core.common.TextBuilder;
import io.github.jwdeveloper.ff.core.files.FileUtility;

import io.github.jwdeveloper.ff.extension.gameobject.neww.impl.core.GameComponent;
import io.github.jwdeveloper.ff.extension.gameobject.neww.prefab.model_loader.LoadeModelFromJson;
import lombok.Data;
import lombok.SneakyThrows;
import org.bukkit.Location;
import org.bukkit.entity.BlockDisplay;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Injection(lifeTime = LifeTime.TRANSIENT)
public class ModeLoaderComponent extends GameComponent {
    public String filePath;


    private GroupModelRepresentation groupModelRepresentation;
    private  List<LoadeModelFromJson.DisplayModel> frame1;
    private  List<LoadeModelFromJson.DisplayModel> frame2;

    private boolean a= false;
    public static boolean shouldRender = true;
    @SneakyThrows
    @Override
    public void onEnable() {

        var filePath1 = "D:\\MC\\spigot_1.19.4\\plugins\\teadasdad\\Model.json";
        var filePath2 = "D:\\MC\\spigot_1.19.4\\plugins\\teadasdad\\Models2.json";

         frame1 = loadFrame(filePath1);
         frame2 = loadFrame(filePath2);
        groupModelRepresentation = new GroupModelRepresentation();
        for (var res : frame1)
        {
            var entity = (BlockDisplay) transform().world().spawnEntity(transform().toLocation(), res.getDisplayType());
            entity.setInterpolationDelay(10);
            entity.setInterpolationDuration(10);
            groupModelRepresentation.add(entity, res);
        }
        groupModelRepresentation.update(transform().toLocation());
    }



    boolean asd = false;
    @Override
    public void onRender() {

        if(shouldRender == true)
        {

            if(asd)
            {
                groupModelRepresentation.setBones(frame1);
            }
            else
            {
                groupModelRepresentation.setBones(frame2);
            }
            asd = !asd;

            logger().info("ASdads");


            shouldRender = false;
            groupModelRepresentation.update(null);
        }

    }

    public List<LoadeModelFromJson.DisplayModel>  loadFrame(String path) throws IOException {

        var fileContent = FileUtility.loadFileContent(path);
        var loader = new LoadeModelFromJson();
        return  loader.FromJson(fileContent);
    }


    public class GroupModelRepresentation
    {
        public List<ModelRepresenation> modelRepresenation = new ArrayList<>();

        public void add(BlockDisplay display, LoadeModelFromJson.DisplayModel b)
        {
          modelRepresenation.add(new ModelRepresenation(display,b));
        }

        public void setBones(List<LoadeModelFromJson.DisplayModel> displayModels)
        {
            var i =0;
            for(var a :  modelRepresenation)
            {
                a.setDisplayModel(displayModels.get(i));
                i++;
            }
        }

        public void update(Location location)
        {
            for(var a : modelRepresenation)
            {
                a.update(location);
            }
        }

    }


    @Data
    public class ModelRepresenation {
        private final BlockDisplay entity;
        private LoadeModelFromJson.DisplayModel displayModel;

        public ModelRepresenation(BlockDisplay display, LoadeModelFromJson.DisplayModel displayModel) {
            this.entity = display;
            this.displayModel = displayModel;
            entity.setBlock(displayModel.getDisplayMeta().getMaterial().createBlockData());
        }

        public void setDisplayModel(LoadeModelFromJson.DisplayModel displayModel) {
            this.displayModel = displayModel;
        }

        public void update() {

            entity.setInterpolationDuration(30);
            entity.setInterpolationDelay(1);
            entity.setTransformationMatrix(displayModel.getTransformationMatrix());
        }

        public void update(Location location) {
            update();
            if (location != null) {
                entity.teleport(location);
            }
        }
    }
}
