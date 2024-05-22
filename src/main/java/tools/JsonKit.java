package tools;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import ymc.basicelements.*;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;


public class JsonKit {

    private static final String json_dir = "books/book_json"; // 假定这是一个安全的目录路径

    public static WordBook jsonToWordBook(String bookName) throws IOException {
        WordBook wordBook = new WordBook(bookName,new ArrayList<Word>());
        ObjectMapper objectMapper = new ObjectMapper();
        String filePath = json_dir+"/"+bookName+".json";
        //System.out.println(filePath);
        try(BufferedReader br = new BufferedReader(new FileReader(filePath))){
            String line;
            while ((line = br.readLine()) != null) {
                //System.out.println(line);
                JsonNode rootNode = objectMapper.readTree(line);

                String bookId = rootNode.get("bookId")!=null ? rootNode.get("bookId").asText() : "[not found]";
                int wordRank = rootNode.get("wordRank")!=null ? rootNode.get("wordRank").asInt() : -1;
                String headWord = rootNode.get("headWord")!=null ? rootNode.get("headWord").asText() : "[not found]";

                JsonNode contentNode = rootNode.get("content").get("word").get("content");

                String usphone = contentNode.get("usphone")!=null ? contentNode.get("usphone").asText() : "[not found]";
                String ukphone = contentNode.get("ukphone")!=null ? contentNode.get("ukphone").asText() : "[not found]";
                if (usphone=="[not found]" && ukphone=="[not found]"){
                    usphone = ukphone = contentNode.get("phone")!=null ? contentNode.get("phone").asText() : "[not found]";
                }
                usphone = "(us)/"+usphone+"/";
                ukphone = "(uk)/"+ukphone+"/";

                //有道英语发音接口https://dict.youdao.com/dictvoice?audio=plastic&type=1{word}&type={1|2}
                String usspeech = contentNode.get("usspeech")!=null ? contentNode.get("usspeech").asText() : "[not found]";
                String ukspeech = contentNode.get("ukspeech")!=null ? contentNode.get("ukspeech").asText() : "[not found]";

                ArrayList<Exam> exams = new ArrayList<>();
                JsonNode examsNode = contentNode.get("exam");
                if (examsNode!=null){
                    for (JsonNode examNode:examsNode){
                        int examType = examNode.get("examType")!=null ? examNode.get("examType").asInt() : -1;
                        String question = examNode.get("question")!=null ? examNode.get("question").asText() : "[not found]";
                        String answer = examNode.get("answer").get("explain")!=null ? examNode.get("answer").get("explain").asText() : "[not found]";
                        int rightIndex = examNode.get("answer").get("rightIndex")!=null ? examNode.get("answer").get("rightIndex").asInt() : -1;
                        JsonNode choicesNode = examNode.get("choices");
                        ArrayList<Choice> choices = new ArrayList<>();
                        for (JsonNode choiceNode:choicesNode){
                            int choiceIndex = choiceNode.get("choiceIndex")!=null ? choiceNode.get("choiceIndex").asInt() : -1;
                            String choice = choiceNode.get("choice")!=null ? choiceNode.get("choice").asText() : "[not found]";
                            choices.add(new Choice(choiceIndex,choice));
                        }
                        exams.add(new Exam(examType,question,answer,rightIndex,choices));
                    }
                }


                ArrayList<Sentence> sentences = new ArrayList<>();
                if (contentNode.get("sentence")!=null){
                    JsonNode sentencesNode = contentNode.get("sentence").get("sentences");
                    for (JsonNode sentenceNode:sentencesNode){
                        String sContent = sentenceNode.get("sContent")!=null ? sentenceNode.get("sContent").asText() : "[not found]";
                        String sCh = sentenceNode.get("sCn")!=null ? sentenceNode.get("sCn").asText() : "[not found]";
                        sentences.add(new Sentence(sContent,sCh));
                    }
                }


                ArrayList<Phrase> phrases = new ArrayList<>();
                if (contentNode.get("phrase")!=null){
                    JsonNode phrasesNode = contentNode.get("phrase").get("phrases");
                    for(JsonNode phraseNode:phrasesNode){
                        String pContent = phraseNode.get("pContent")!=null ? phraseNode.get("pContent").asText() : "[not found]";
                        String pCn = phraseNode.get("pCn")!=null ? phraseNode.get("pCn").asText() : "[not found]";
                        phrases.add(new Phrase(pContent,pCn));
                    }
                }

                ArrayList<Syno> synos = new ArrayList<>();
                if (contentNode.get("syno")!=null){
                    JsonNode synosNode = contentNode.get("syno").get("synos");
                    for(JsonNode synoNode:synosNode){
                        String pos = synoNode.get("pos")!=null ? synoNode.get("pos").asText() : "[not found]";
                        String tran = synoNode.get("tran")!=null ? synoNode.get("tran").asText() : "[not found]";
                        JsonNode hwdsNode = synoNode.get("hwds");
                        ArrayList<String> hwds = new ArrayList<>();
                        for(JsonNode hwdNode:hwdsNode){
                            String hwd = hwdNode.get("w")!=null ? hwdNode.get("w").asText() : "[not found]";
                            hwds.add(hwd);
                        }
                        Syno syno = new Syno(pos,tran,hwds);
                        synos.add(syno);
                    }
                }



                ArrayList<RelWord> rels = new ArrayList<>();
                if (contentNode.get("relWord")!=null){
                    JsonNode relsNode = contentNode.get("relWord").get("rels");
                    for(JsonNode relNode:relsNode){
                        String pos = relNode.get("pos")!=null ? relNode.get("pos").asText() : "[not found]";
                        JsonNode wordsNode = relNode.get("words");
                        ArrayList<String> hwds = new ArrayList<>();
                        ArrayList<String> trans = new ArrayList<>();
                        for(JsonNode wordNode:wordsNode){
                            String hwd = wordNode.get("hwd")!=null ? wordNode.get("hwd").asText() : "[not found]";
                            String tran = wordNode.get("tran")!=null ? wordNode.get("tran").asText() : "[not found]";
                            hwds.add(hwd);
                            trans.add(tran);
                        }
                        RelWord relWord = new RelWord(pos,hwds,trans);
                        rels.add(relWord);
                    }
                }

                StringBuilder tranChinese = new StringBuilder();
                StringBuilder tranEnglish = new StringBuilder();
                ArrayList<Tran> trans = new ArrayList<>();
                JsonNode transNode = contentNode.get("trans");
                if (transNode!=null){
                    for (JsonNode tranNode : transNode){
                        String pos = tranNode.get("pos")!=null ? tranNode.get("pos").asText() : "[not found]";
                        String tranCn = tranNode.get("tranCn")!=null ? tranNode.get("tranCn").asText() : "[not found]";
                        String tranEng = tranNode.get("tranOther")!=null ? tranNode.get("tranOther").asText() : "[not found]";
                        Tran tran = new Tran(pos,tranCn,tranEng);
                        trans.add(tran);
                        tranChinese.append("|").append(pos).append(". ").append(tranCn);
                        tranEnglish.append("|").append(pos).append(". ").append(tranEng);
                    }
                }

                Word word = new Word(bookId,wordRank,headWord,tranChinese.toString(),tranEnglish.toString(),exams,sentences,synos,phrases,rels,trans,usphone,ukphone,ukspeech,usspeech);
                wordBook.addWord(word);
            }
        }


    return wordBook;
    }

}

