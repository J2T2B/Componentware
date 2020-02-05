package supportgame.members;

import com.google.gson.Gson;
import org.apache.commons.lang3.RandomUtils;

import java.io.*;
import java.net.URL;
import java.util.Enumeration;

public class ProfileLoader {

    private static Profile[] profiles;

    private static void loadProfiles() throws IOException{
        Enumeration<URL> url = ProfileLoader.class.getClassLoader().getResources("profiles.json");
        if (url.hasMoreElements()) {
            InputStream is = url.nextElement().openStream();

            StringBuilder sb = new StringBuilder();
            try {
                Reader r = new InputStreamReader(is, "UTF-8");
                int c = 0;
                while ((c = r.read()) != -1) {
                    sb.append((char) c);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            Gson gson = new Gson();
            profiles = gson.fromJson(sb.toString(), Profile[].class);
        }
    }

    public static Profile[] getProfiles() {
        if (profiles == null) {
            try {
                loadProfiles();
            } catch (Exception ignored) {
                // :)
            }
        }
        return profiles;
    }

    public static Profile getRandom() {
        getProfiles();
        return profiles[RandomUtils.nextInt(0, profiles.length)];
    }
}
