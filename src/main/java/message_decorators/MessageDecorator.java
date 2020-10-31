package message_decorators;

public class MessageDecorator {
    private static final DothrakiTranslator dh = new DothrakiTranslator();


    public String decor(String message){
        StringBuilder sb = new StringBuilder(message);
        String line = "\n\n--- Dothraki translation: ---\n";
        String translatedMessage = dh.getTranslation(message);

        sb.append(line);
        sb.append(translatedMessage);
        return sb.toString();
    }

    public static void main(String[] args) {
        MessageDecorator md = new MessageDecorator();
        System.out.println(md.decor("I love chocolate and horses"));
    }

}
