import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 * 第 1 天：不用任何框架，直接调一次大模型接口。
 * 运行前确认已设置环境变量 DEEPSEEK_API_KEY。
 */
public class Day1 {
    public static void main(String[] args) throws Exception {
        String apiKey = System.getenv("DEEPSEEK_API_KEY");
        if (apiKey == null || apiKey.isBlank()) {
            System.err.println("没有读到环境变量 DEEPSEEK_API_KEY，设置后需要重启 IDEA。");
            return;
        }

        String body = """
            {
              "model": "deepseek-chat",
              "temperature": 1.0,
              "messages": [
                {"role": "system", "content": "你是一个云平台运维助手"},
                {"role": "user", "content": "云盘挂载失败一般有哪些原因？"}
              ]
            }
            """;

        HttpRequest request = HttpRequest.newBuilder()
            .uri(URI.create("https://api.deepseek.com/chat/completions"))
            .header("Content-Type", "application/json")
            .header("Authorization", "Bearer " + apiKey)
            .POST(HttpRequest.BodyPublishers.ofString(body))
            .build();

        HttpResponse<String> response = HttpClient.newHttpClient()
            .send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println("HTTP 状态码: " + response.statusCode());
        System.out.println(response.body());
    }
}
