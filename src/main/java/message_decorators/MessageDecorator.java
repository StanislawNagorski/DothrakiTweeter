package message_decorators;

public class MessageDecorator {
    private static final DothrakiTranslator dh = new DothrakiTranslator();


    public String decor(String message){
        StringBuilder sb = new StringBuilder(message);
        String nextLine = "<br>";
        String line = "--- Dothraki translation: ---";
        String translatedMessage = dh.getTranslation(message);

        sb.append(nextLine.repeat(2));
        sb.append(line);
        sb.append(nextLine);
        sb.append(translatedMessage);
        sb.append(nextLine);
        return sb.toString();
    }

}
