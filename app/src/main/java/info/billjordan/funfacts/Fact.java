package info.billjordan.funfacts;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by bill on 7/11/15.
 */
public class Fact implements Parcelable {
    private String label;
    private int id;
    private String question;
    private String answer;
    private HashMap<String, String> answers;
    private String correctAnswerNumber;
    private int categoryId;


    public Fact(String label, int id) {
        this.label = label;
        this.id = id;
        this.question = "Fact Question Placeholder";
        this.answer = "Fact Answer Placeholder";
    }

    /**
     *
     * @param label
     * @param id
     * @param question
     * @param answers
     * @param correctAnswerNumber
     * @param categoryId
     */
    public Fact(String label,
                int id,
                String question,
                HashMap<String, String> answers,
                String correctAnswerNumber,
                int categoryId
    ){
        this.label = label;
        this.id = id;
        this.question = question;
        this.answers = answers;
        this.correctAnswerNumber = correctAnswerNumber;
        this.categoryId = categoryId;
        this.answer = answers.get(correctAnswerNumber);
    }

    private Fact (Parcel in){
        this.label = in.readString();
        this.id = in.readInt();
        this.question = in.readString();
        this.answer = in.readString();
        //this.answers = in.readMap(answers, Map<String, String>.class);
        //see write to Parcel
        int answersMapSize = in.readInt();
        for(int i=0; i < answersMapSize; i++){
            String key = in.readString();
            String value = in.readString();
            this.answers.put(key, value);
        }
        this.correctAnswerNumber = in.readString();
        this.categoryId = in.readInt();
    }

    public String getAnswerNumber(int number){
        return answers.get(String.valueOf(number));
    }

    public String getAnswerNumber(String number){
        return answers.get(number);
    }

    public String getLabel() {
        return label;
    }

    //returns correct answer
    public String getAnswer() {
        return answer;
    }

    public String getQuestion() {
        return question;
    }

    public int getId() {
        return id;
    }

    /**
     * Returns a string containing a concise, human-readable description of this
     * object. Subclasses are encouraged to override this method and provide an
     * implementation that takes into account the object's type and data. The
     * default implementation is equivalent to the following expression:
     * <pre>
     *   getClass().getName() + '@' + Integer.toHexString(hashCode())</pre>
     * <p>See <a href="{@docRoot}reference/java/lang/Object.html#writing_toString">Writing a useful
     * {@code toString} method</a>
     * if you intend implementing your own {@code toString} method.
     *
     * @return a printable representation of this object.
     */
    @Override
    public String toString() {
        return label;
    }

    /**
     * Describe the kinds of special objects contained in this Parcelable's
     * marshalled representation.
     *
     * @return a bitmask indicating the set of special object types marshalled
     * by the Parcelable.
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Flatten this object in to a Parcel.
     *
     * @param dest  The Parcel in which the object should be written.
     * @param flags Additional flags about how the object should be written.
     *              May be 0 or {@link #PARCELABLE_WRITE_RETURN_VALUE}.
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(label);
        dest.writeInt(id);
        dest.writeString(question);
        dest.writeString(answer);
        //dest.writeMap(answers);
        //writeMap is deprecated
        dest.writeInt(answers.size());
        for(Map.Entry<String, String> entry : answers.entrySet()){
            dest.writeString(entry.getKey());
            dest.writeString(entry.getValue());
        }
        dest.writeString(correctAnswerNumber);
        dest.writeInt(categoryId);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator(){
        /**
         * Create a new instance of the Parcelable class, instantiating it
         * from the given Parcel whose data had previously been written by
         * {@link Parcelable#writeToParcel Parcelable.writeToParcel()}.
         *
         * @param source The Parcel to read the object's data from.
         * @return Returns a new instance of the Parcelable class.
         */
        @Override
        public Object createFromParcel(Parcel source) {
            return new Fact(source);
        }

        /**
         * Create a new array of the Parcelable class.
         *
         * @param size Size of the array.
         * @return Returns an array of the Parcelable class, with every entry
         * initialized to null.
         */
        @Override
        public Object[] newArray(int size) {
            return new Object[0];
        }
    };
}
