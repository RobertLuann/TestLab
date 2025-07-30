package com.ufersa.testlab.model.services;

import com.ufersa.testlab.model.entities.*;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GeradorProvaPdfService {

    private final PDDocument document;
    private PDPageContentStream contentStream;
    private float y; // Posição vertical atual na página
    private final float margin = 50;
    private final float larguraUtil;
    private final PDPage pagina;
    private final PDType1Font fonteBold = new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD);
    private final PDType1Font fonteNormal = new PDType1Font(Standard14Fonts.FontName.HELVETICA);
    private final PDType1Font fonteItalico = new PDType1Font(Standard14Fonts.FontName.HELVETICA_OBLIQUE);

    // Construtor que prepara o documento
    public GeradorProvaPdfService() throws IOException {
        this.document = new PDDocument();
        this.pagina = new PDPage(PDRectangle.A4);
        document.addPage(pagina);
        this.contentStream = new PDPageContentStream(document, pagina);
        this.larguraUtil = pagina.getMediaBox().getWidth() - 2 * margin;
        this.y = pagina.getMediaBox().getHeight() - margin;
    }

    // Método principal que gera o PDF
    public void gerarProva(Prova prova, String caminhoDestino) throws IOException {
        try {
            escreverCabecalho(prova);
            escreverQuestoes(prova.getQuestoes());

            contentStream.close();
            document.save(caminhoDestino);
        } finally {
            document.close();
        }
    }

    private void escreverCabecalho(Prova prova) throws IOException {
        escreverTexto(fonteBold, 16, "Avaliação de " + prova.getDisciplina().getNome());
        y -= 15;
        escreverTexto(fonteNormal, 12, "Título: " + prova.getTitulo());
        y -= 25;
        escreverTexto(fonteNormal, 12, "Nome do Aluno(a): _________________________________________");
        y -= 40;
    }

    private void escreverQuestoes(List<Questao> questoes) throws IOException {
        int numeroQuestao = 1;
        for (Questao questao : questoes) {
            String textoQuestao = numeroQuestao + ". " + questao.getEnunciado();
            List<String> linhasEnunciado = quebrarLinhas(textoQuestao, fonteNormal, 12, larguraUtil);

            // Verifica se a questão inteira cabe na página atual
            verificarEspacoNaPagina(linhasEnunciado.size() * 15f + 30f);

            for (String linha : linhasEnunciado) {
                escreverTexto(fonteNormal, 12, linha);
            }

            if (questao instanceof QuestaoMultiplaEscolha qme) {
                y -= 10;
                char letraAlternativa = 'a';
                for (Alternativa alt : qme.getAlternativas()) {
                    verificarEspacoNaPagina(15f);
                    escreverTexto(fonteNormal, 12, "   (" + letraAlternativa + ") " + alt.getTexto());
                    letraAlternativa++;
                }
            }

            y -= 25; // Espaço extra entre as questões
            numeroQuestao++;
        }
    }

    // Ferramenta para escrever texto e gerenciar a posição 'y'
    private void escreverTexto(PDType1Font fonte, int fontSize, String texto) throws IOException {
        contentStream.beginText();
        contentStream.setFont(fonte, fontSize);
        contentStream.newLineAtOffset(margin, y);
        contentStream.showText(texto);
        contentStream.endText();
        y -= (fontSize * 1.2f); // Move a posição 'y' para a próxima linha
    }

    // Verifica se há espaço na página, se não, cria uma nova
    private void verificarEspacoNaPagina(float espacoNecessario) throws IOException {
        if (y - espacoNecessario < margin) {
            contentStream.close();
            PDPage novaPagina = new PDPage(PDRectangle.A4);
            document.addPage(novaPagina);
            contentStream = new PDPageContentStream(document, novaPagina);
            y = novaPagina.getMediaBox().getHeight() - margin;
        }
    }

    // Método auxiliar para quebrar textos longos em várias linhas
    private static List<String> quebrarLinhas(String texto, PDType1Font fonte, int fontSize, float maxWidth) throws IOException {
        List<String> lines = new ArrayList<>();
        String[] words = texto.split(" ");
        StringBuilder currentLine = new StringBuilder();

        for (String word : words) {
            float width = fonte.getStringWidth(currentLine + " " + word) / 1000 * fontSize;
            if (width > maxWidth) {
                lines.add(currentLine.toString());
                currentLine = new StringBuilder(word);
            } else {
                if (!currentLine.isEmpty()) {
                    currentLine.append(" ");
                }
                currentLine.append(word);
            }
        }
        lines.add(currentLine.toString());
        return lines;
    }
}