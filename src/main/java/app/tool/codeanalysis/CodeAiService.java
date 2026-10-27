package app.tool.codeanalysis;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import okhttp3.*;
import app.tool.codeanalysis.sourcecode.processors.model.TreeNode;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class CodeAiService {
    private final OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(600, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .readTimeout(600, TimeUnit.SECONDS)
            .connectionPool(new ConnectionPool(5, 5, TimeUnit.MINUTES))
            .build();
    private static final String BASE_URL = "http://192.168.66.224:8080/v1/responses";
    private static final String TOKEN = "sk-8dae582ca97023127dcb8d7b29098f8747b0cf28494c9965d4a4b246b50865f8";
    private static final String MODEL_NAME = "Qwen3.8-27B";
    private static final String ACTOR_HEADER_VAL = "local-image-extension";

    public TreeNode askAi(TreeNode treeNode) throws IOException {
        String prompt = JSON.toJSONString(treeNode);
        // OpenAI标准chat请求体
        Map<String, Object> reqBody = Map.of(
                "model", MODEL_NAME,
                "messages", List.of(
                        Map.of("role", "system", "content", "你是Java源码阅读助手，专门解析框架源码，输出简洁，不要markdown，适合弹窗展示。"),
                        Map.of("role", "user", "content", "我解析SpringBoot2.6.1框架源码启动流程,封装在了以下TreeNode对象树中,需要你针对整个调用流程挑关键节点说明调用的作用或者设计意图,并设置在TreeNode的aiAnalysis字段中,再以json格式返回: " + prompt)
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
        String aiResult = jsonObject.getJSONArray("output")
                .getJSONObject(1)
                .getJSONArray("content")
                .getJSONObject(0)
                .getString("text");

        return JSON.to(TreeNode.class, aiResult);
    }
}
