package pt.ubi.di.pmd.examcrash;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import pt.ubi.di.pmd.examcrash.ExamContract.*;

//import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ExamDBHelper extends SQLiteOpenHelper {
    private static final  String DATABASE_NAME = "MyExamCrash.db";
    private static final int DATABASE_VERSION = 1;

    private SQLiteDatabase db;

    public ExamDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;

        final String SQL_CREATE_QUESTIONS_TABLE = "CREATE TABLE " + QuestionsTable.TABLE_NAME +
                "(" + QuestionsTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                QuestionsTable.COLUMN_QUESTION + " TEXT, " +
                QuestionsTable.COLUMN_OPTION1 + " TEXT, " +
                QuestionsTable.COLUMN_OPTION2 + " TEXT, " +
                QuestionsTable.COLUMN_OPTION3 + " TEXT, " +
                QuestionsTable.COLUMN_OPTION4 + " TEXT, " +
                QuestionsTable.COLUMN_ANSWER + " INTEGER " +
                ")";

        db.execSQL(SQL_CREATE_QUESTIONS_TABLE);
        fillQuestionsTable();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + QuestionsTable.TABLE_NAME);
        onCreate(db);
    }

    private void fillQuestionsTable(){
        Question q1 = new Question(" O que significa, para si, IDE?\n",
                "Integrated Development Enviroment",
                "Internet Development Key",
                "Interface Default Know",
                "Integrated Developer Kit", 1 );
        addQuestion(q1);
        Question q2 = new Question(" O acrónimo\n" +
                "SDK, é abreviatura de quê?",
                "Simple Delivery Kit",
                "Software Development Kit",
                "Simple Developement Kit",
                "Super Donkey Kong", 2);
        addQuestion(q2);
        Question q3 = new Question("Qual das seguintes opções define corretamente SDK?\n",
                "Software Development Kit",
                "System Development Kit",
                "É um conjunto de ferramentas de desenvolvimento de software que permitem a criação de aplicações para um dado pacote, " +
                        "sistema de software, plataforma de hardware, computador, consola de jogos de vídeo, sistema operativo" +
                        " ou outra plataforma de desenvolvimento semelhante.",
                "System Duplicate Kit", 3);
        addQuestion(q3);
        Question q4 = new Question("Qual é o núcleo base do iOS?",
                "Unix",
                "MSDOS",
                "Linux",
                "Windows", 1);
        addQuestion(q4);
        Question q5 = new Question("O que significa o X do acrónimo XML?\n",
                "HyperteXt",
                "eXtensible",
                "Language",
                "Linux", 2);
        addQuestion(q5);
    }

    private void addQuestion(Question question){
        ContentValues cv = new ContentValues();
        cv.put(QuestionsTable.COLUMN_QUESTION, question.getQuestion());
        cv.put(QuestionsTable.COLUMN_OPTION1, question.getOption1());
        cv.put(QuestionsTable.COLUMN_OPTION2, question.getOption2());
        cv.put(QuestionsTable.COLUMN_OPTION3, question.getOption3());
        cv.put(QuestionsTable.COLUMN_OPTION4, question.getOption4());
        cv.put(QuestionsTable.COLUMN_ANSWER, question.getAnswerNumber());
        db.insert(QuestionsTable.TABLE_NAME, null, cv);
    }

    @SuppressLint("Range")
    public List<Question> getAllQuestions(){
        List<Question> questionList = new ArrayList<>();
        db = getReadableDatabase();
        Cursor c = db.rawQuery( "SELECT * FROM " + QuestionsTable.TABLE_NAME, null);

        if(c.moveToFirst()){
            do{
                Question question = new Question();
                question.setQuestion(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_QUESTION)));
                question.setOption1(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION1)));
                question.setOption2(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION2)));
                question.setOption3(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION3)));
                question.setOption4(c.getString(c.getColumnIndex(QuestionsTable.COLUMN_OPTION4)));
                question.setAnswerNumber(c.getInt(c.getColumnIndex(QuestionsTable.COLUMN_ANSWER)));
                questionList.add(question);
            }while (c.moveToNext());
        }
        c.close();
        return questionList;
    }
}
