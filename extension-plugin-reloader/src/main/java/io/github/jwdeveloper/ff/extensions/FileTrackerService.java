package io.github.jwdeveloper.ff.extensions;
import io.github.jwdeveloper.ff.core.async.cancelation.CancelationToken;
import io.github.jwdeveloper.ff.core.spigot.tasks.api.FluentTaskFactory;
import java.io.IOException;
import java.nio.file.*;

public class FileTrackerService {
    private  final Path directory = Paths.get("D:\\MC\\spigot_1.19.4\\plugins");
    private final  FluentTaskFactory taskFactory;

    public FileTrackerService(FluentTaskFactory taskFactory) {
        this.taskFactory = taskFactory;
    }

    public void startAsync(CancelationToken ctx) throws IOException, InterruptedException {
        var watchService = FileSystems.getDefault().newWatchService();
        directory.register(watchService, StandardWatchEventKinds.ENTRY_MODIFY);
        taskFactory.taskAsync(() ->
        {
            try
            {
                taskBody(watchService, ctx);
            } catch (InterruptedException e)
            {
                throw new RuntimeException(e);
            }
        },ctx);
    }

    private  void taskBody(WatchService watchService, CancelationToken ctx) throws InterruptedException
    {
        while (ctx.isNotCancel())
        {
            WatchKey key = watchService.take();
            for (WatchEvent<?> event : key.pollEvents()) {
                WatchEvent.Kind<?> kind = event.kind();
                if (kind == StandardWatchEventKinds.ENTRY_MODIFY) {
                    Path fileName = (Path) event.context();
                    System.out.println("File modified: " + fileName+" SERICES ID"+key);
                }
            }
            key.reset();
        }
    }

}
