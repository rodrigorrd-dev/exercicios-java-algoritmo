import java.io.*;

public class Main {
    public static void main(String[] args) {
        // Caminhos completos dos arquivos
        String inputFile = "input.txt";
        String outputFile = "output.txt";

        try (
                BufferedReader arquivoAtual = new BufferedReader(new FileReader(inputFile));
                BufferedWriter arquivoNovo = new BufferedWriter(new FileWriter(outputFile))
        ) {
            String linha;
            /**
             * Enquanto a linha for diferente de null e se a linha nao
             * estiver vazia ele pega o que esta escrito e adiciona a linha no novo arquivo
             */
            while ((linha = arquivoAtual.readLine()) != null) {
                if (!linha.trim().isEmpty()) { // ignora linhas em branco
                    arquivoNovo.write(linha);
                    arquivoNovo.newLine();
                }
            }
            System.out.println("Arquivo corrigido gerado em: " + outputFile);
        } catch (IOException e) {
            System.err.println("Erro ao processar o arquivo: " + e.getMessage());
        }
    }
}