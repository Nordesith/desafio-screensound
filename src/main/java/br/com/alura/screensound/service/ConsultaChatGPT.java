package br.com.alura.screensound.service;

import com.theokanning.openai.OpenAiHttpException;
import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.service.OpenAiService;

public class ConsultaChatGPT {
    public static String obterInformacao(String texto) {
        try {
            OpenAiService service = new OpenAiService(System.getenv("CHAVE_OPENAI"));

            CompletionRequest requisicao = CompletionRequest.builder()
                    .model("gpt-3.5-turbo-instruct")
                    .prompt("me fale sobre o artista: " + texto)
                    .maxTokens(1000)
                    .temperature(0.7)
                    .build();

            var resposta = service.createCompletion(requisicao);
            return resposta.getChoices().get(0).getText();
        } catch (OpenAiHttpException e) {
            String error;
            if (e.getMessage().contains("You exceeded your current quota")) {
                error = "Desculpe, excedemos a cota de uso da API. Por favor, tente novamente mais tarde.";
            } else {
                throw e; // Relança a exceção se não for o erro de cota
            }
            return error;
        }
    }
}
