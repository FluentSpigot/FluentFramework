package io.github.jwdeveloper.ff.extension.updater.implementation.providers;

import com.google.gson.JsonParser;
import io.github.jwdeveloper.ff.core.common.java.StringUtils;
import io.github.jwdeveloper.ff.extension.updater.api.UpdateInfoProvider;
import io.github.jwdeveloper.ff.extension.updater.api.info.UpdateInfoResponse;
import io.github.jwdeveloper.ff.extension.updater.api.options.GithubUpdaterOptions;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class GithubInfoProvider implements UpdateInfoProvider {
    private final GithubUpdaterOptions options;
    public GithubInfoProvider(GithubUpdaterOptions options) {
        this.options = options;
    }


    private URL getRequestUrl() throws MalformedURLException
    {
        if(StringUtils.isNullOrEmpty(options.getGithubUserName()))
        {
            throw new RuntimeException("Github user name should not be null");
        }
        if(StringUtils.isNullOrEmpty(options.getRepositoryName()))
        {
               throw new RuntimeException("Repository name should not be null");
        }


        var link = String.format("https://api.github.com/repos/%s/%s/releases/latest", options.getGithubUserName(), options.getRepositoryName());
        return new URL(link);
    }

    @Override
    public UpdateInfoResponse getUpdateInfo()
    {
        try
        {
            var response = doRequest();
            return mapToInfoResponse(response);
        }
        catch (FileNotFoundException fileNotFoundException)
        {
            return UpdateInfoResponse.NOT_FOUND;
        }
        catch (RuntimeException error)
        {
            return UpdateInfoResponse.NOT_FOUND;
        }
        catch (Exception e)
        {
          throw new RuntimeException("Unable to do update request",e);
        }
    }

    private String doRequest() throws IOException {
        var builder = new StringBuilder();
        var url = getRequestUrl();
        var conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        try (var reader = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
            var line = StringUtils.EMPTY;
            while (true) {
                line = reader.readLine();
                if (line == null)
                    break;
                builder.append(line);
            }
        } catch (Exception e) {
            throw new RuntimeException("Unable to get content from update request", e);
        }

        if (conn.getResponseCode() != 200) {
            throw new RuntimeException("update request invalid response code");
        }


        return builder.toString();
    }

    private UpdateInfoResponse mapToInfoResponse(String content) {
        var json = new JsonParser().parse(content).getAsJsonObject();
        var result = new UpdateInfoResponse();

        var assetsElement = json.getAsJsonArray("assets");
        for (var element : assetsElement) {
            var elementJson = element.getAsJsonObject();
            var name = elementJson.get("name").getAsString();
            if (!name.contains(".jar")) {
                continue;
            }

            var downloadUrl = elementJson.get("browser_download_url").getAsString();
            result.setFileName(name);
            result.setDownloadUrl(downloadUrl);
        }


        var description = json.get("body").getAsString();
        var version = json.get("tag_name").getAsString();

        result.setVersion(version);
        result.setDescription(description);
        return result;
    }

}
