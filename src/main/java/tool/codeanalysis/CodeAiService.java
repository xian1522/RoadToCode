package tool.codeanalysis;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import okhttp3.*;
import sourcecode.processors.model.TreeNode;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class CodeAiService {
    private final OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .connectionPool(new ConnectionPool(5, 5, TimeUnit.MINUTES))
            .build();
    private static final String BASE_URL = "";
    private static final String TOKEN = "";
    private static final String MODEL_NAME = "Qwen3.8-27B";
    private static final String ACTOR_HEADER_VAL = "local-image-extension";

    public static void main(String[] args) throws IOException {
        CodeAiService aiService = new CodeAiService();
        TreeNode treeNode = new CodeAnalysisService().codeAnalysis();
        TreeNode resultNode = aiService.askAi(treeNode);
        System.out.println(resultNode);
    }
    public TreeNode askAi(TreeNode treeNode) throws IOException {
        String prompt = JSON.toJSONString(treeNode);
        // OpenAI标准chat请求体
        Map<String, Object> reqBody = Map.of(
                "model", MODEL_NAME,
                "messages", List.of(
                        Map.of("role", "system", "content", "你是Java源码阅读助手，专门解析框架源码，输出简洁，不要markdown，适合弹窗展示。"),
                        Map.of("role", "user", "content", "对以下json格式的SpringBoot2.6.1框架源码做解析,说明每步调用的作用或者设计意图,并设置在aiAnalysis字段中,再原封不动的以json格式返回: " + prompt)
                ),
                "stream", false
        );
        String jsonBody = JSON.toJSONString(reqBody);
        RequestBody body = RequestBody.create(jsonBody, MediaType.get("application/json"));

        Request request = new Request.Builder()
                .url(BASE_URL)
                .post(body)
                // Bearer鉴权
                .header("Authorization", "Bearer " + TOKEN)
                // 你配置里额外要求的header
                .header("x-openai-actor-authorization", ACTOR_HEADER_VAL)
                .build();

        Response response = client.newCall(request).execute();
        JSONObject jsonObject = JSON.parseObject(response.body().string());
        String aiResult = jsonObject.getJSONArray("choices")
                .getJSONObject(0)
                .getJSONObject("message")
                .getString("content");

        return JSON.to(TreeNode.class, aiResult);
    }
}
