package util.skin;

import java.io.InputStreamReader;
import java.net.URL;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;


public class GetSkin {
	public static String[] getSkin(String name) {
		try {
			URL url = new URL("https://api.mojang.com/users/profiles/minecraft/" + name);
			InputStreamReader r = new InputStreamReader(url.openStream());
			String uuid = ((JSONObject)new JSONParser().parse(r)).get("id").toString();
			URL url2 = new URL("https://sessionserver.mojang.com/session/minecraft/profile/" + uuid
				+ "?unsigned=false");
			InputStreamReader r2 = new InputStreamReader(url2.openStream());
			JSONObject obj = (JSONObject)((JSONArray)((JSONObject)new JSONParser().parse(r2)).get("properties")).get(0);
			String value = obj.get("value").toString();
			String signature = obj.get("signature").toString();
			return new String[] {value, signature};
		}catch(Exception e) {
			return new String[] {"ewogICJ0aW1lc3RhbXAiIDogMTY3NzI5NTcwNjk4NCwKICAicHJvZmlsZUlkIiA6ICJmMmFmMzZkMjVlMjU0OWQyYjk4OThhMzMyN2M2NTBmYiIsCiAgInByb2ZpbGVOYW1lIiA6ICJVTklWRVJTRUZSSUVORFMiLAogICJzaWduYXR1cmVSZXF1aXJlZCIgOiB0cnVlLAogICJ0ZXh0dXJlcyIgOiB7CiAgICAiU0tJTiIgOiB7CiAgICAgICJ1cmwiIDogImh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjMxNjU1NTAyNmZkODFlMTIxNGJiZWRlNjQ1NTczNDA5NGE1MjE2NmU3MTQyNTU4NmRhNjRmZGMyMzU2OGY1ZCIKICAgIH0KICB9Cn0=", 
			"DwpTYCFufF1gRXVKwlCYotDVSCDqNfNY5BQS+E+a7oKDJRshlx3CKZavJOIJ5vwugDHq3QKrmc5Ns+Rn2gqWCsx5n+ERMOY3VHzSJLSRpFDdOhzLhfbUTz5m9ge7zvF6jxU9h8OduOjcVA5rtjcbreTsas+5u3g35LCOUI8aKy29NoUxKn8dx8ZhoxughcLy0lgV4lag6oq38GaGFGk38ApOAL0Iw6KycbA94K+by+kTf+OsfGPtWpiUoqOnJJMk7PQHGO7UhDMNmZEIWwaRi4KJsw01L959+eRLAvV89I7KiTNv1ngR1ka2gtALdjTUyptW/hNnZQjPypDu7upqUJ6imdkjQWdKp3neGbEI8YpS3ihB23dKbbqqJek0H8j8dI/tlAH4jwAvDBeFlMrybrEZEBAs05fsbXzPse6pm8J1p+R5iZKVoNW3V+1CwsY/773tmjVgbhyowEa8211egbFAVZbk2Yzc4qaeZMgIP+CW94MvT7dzhzSpu4xeApkzcWnAVGAHXLePUk88zH/KjooXLQ4cMAS7+rvbyAsO/mft9FtyDOOOhPm2Jhgf/PhWlS3F6t9CHO81HugkYE9OqsXCAIMItYrT0hPNzf9BLySThpxRaVRLGGRAJjoG36cA7zx4GsaPjflHMiRrRWg2ExGoc3ffNqVxcCZaFnseN5o="};
		}
	}
}
