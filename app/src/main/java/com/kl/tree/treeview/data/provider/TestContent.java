package com.kl.tree.treeview.data.provider;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.net.Uri;
import android.util.Log;

import com.kl.tree.treeview.data.provider.util.ColumnMetadata;


/**
 * This class was generated by the ContentProviderCodeGenerator project made by Foxykeep
 * <p>
 * (More information available https://github.com/foxykeep/ContentProviderCodeGenerator)
 */
public abstract class TestContent {

    public static final Uri CONTENT_URI = Uri.parse("content://" + TestProvider.AUTHORITY);

    private TestContent() {
    }

    /**
     * Created in version 1
     */
    public static final class leaf extends TestContent {

        private static final String LOG_TAG = leaf.class.getSimpleName();

        public static final String TABLE_NAME = "leaf";
        public static final String TYPE_ELEM_TYPE = "vnd.android.cursor.item/-leaf";
        public static final String TYPE_DIR_TYPE = "vnd.android.cursor.dir/-leaf";

        public static final Uri CONTENT_URI = Uri.parse(TestContent.CONTENT_URI + "/" + TABLE_NAME);

        public static enum Columns implements ColumnMetadata {
            ID("id", "integer"),
            SRC_ID("src_id", "integer"),
            NAME("name", "text"),
            PHONE("phone", "text"),
            SEX("sex", "text");

            private final String mName;
            private final String mType;

            private Columns(String name, String type) {
                mName = name;
                mType = type;
            }

            @Override
            public int getIndex() {
                return ordinal();
            }

            @Override
            public String getName() {
                return mName;
            }

            @Override
            public String getType() {
                return mType;
            }
        }

        public static final String[] PROJECTION = new String[] {
                Columns.ID.getName(),
                Columns.SRC_ID.getName(),
                Columns.NAME.getName(),
                Columns.PHONE.getName(),
                Columns.SEX.getName(),
        };

        private leaf() {
            // No private constructor
        }

        public static void createTable(SQLiteDatabase db) {
            if (TestProvider.ACTIVATE_ALL_LOGS) {
                Log.d(LOG_TAG, "leaf | createTable start");
            }
            /*db.execSQL("CREATE TABLE " + TABLE_NAME + " (" + Columns.ID.getName() + " " + Columns.ID.getType() + ", " + Columns.SRC_ID.getName() + " " + Columns.SRC_ID.getType() + ", " + Columns.STATUS.getName() + " " + Columns.STATUS.getType() + ", " + Columns.CHS_NAME.getName() + " " + Columns.CHS_NAME.getType() + ", " + Columns.WQP_CODE.getName() + " " + Columns.WQP_CODE.getType() + ", " + Columns.EXTNO.getName() + " " + Columns.EXTNO.getType() + ", " + Columns.PRIVACY.getName() + " " + Columns.PRIVACY.getType() + ", " + Columns.TEL_PRIMARY.getName() + " " + Columns.TEL_PRIMARY.getType() + ", " + Columns.TEL_MOBILE.getName() + " " + Columns.TEL_MOBILE.getType() + ", " + Columns.TEL_OFFICE.getName() + " " + Columns.TEL_OFFICE.getType() + ", " + Columns.TEL_HOME.getName() + " " + Columns.TEL_HOME.getType() + ", " + Columns.TEL_ELSE.getName() + " " + Columns.TEL_ELSE.getType() + ", " + Columns.TEL_ELSE2.getName() + " " + Columns.TEL_ELSE2.getType() + ", " + Columns.GENDER.getName() + " " + Columns.GENDER.getType() + ", " + Columns.JOB_GRADE.getName() + " " + Columns.JOB_GRADE.getType() + ", " + Columns.JOB_TITLE.getName() + " " + Columns.JOB_TITLE.getType() + ", " + Columns.JOB_TITLE_WQP.getName() + " " + Columns.JOB_TITLE_WQP.getType() + ", " + Columns.STAFF_SORT.getName() + " " + Columns.STAFF_SORT.getType() + ", " + Columns.VPN_VERSION.getName() + " " + Columns.VPN_VERSION.getType() + ", " + Columns.EMAIL.getName() + " " + Columns.EMAIL.getType() + ", " + Columns.IM_USER.getName() + " " + Columns.IM_USER.getType() + ", PRIMARY KEY (" + Columns.ID.getName() + ")" + ");");

            if (TestProvider.ACTIVATE_ALL_LOGS) {
                Log.d(LOG_TAG, "leaf | createTable end");
            }*/

            //DBHelper.getInstance().formeDb();
        }

        // Version 1 : Creation of the table
        public static void upgradeTable(SQLiteDatabase db, int oldVersion, int newVersion) {
            if (TestProvider.ACTIVATE_ALL_LOGS) {
                Log.d(LOG_TAG, "leaf | upgradeTable start");
            }

            if (oldVersion < 1) {
                Log.i(LOG_TAG, "Upgrading from version " + oldVersion + " to " + newVersion
                        + ", data will be lost!");

                db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME + ";");
                createTable(db);
                return;
            }


            if (oldVersion != newVersion) {
                throw new IllegalStateException("Error upgrading the database to version "
                        + newVersion);
            }

            if (TestProvider.ACTIVATE_ALL_LOGS) {
                Log.d(LOG_TAG, "leaf | upgradeTable end");
            }
        }

        static String getBulkInsertString() {
            return new StringBuilder("INSERT INTO ").append(TABLE_NAME).append(" ( ").append(Columns.ID.getName()).append(", ").append(Columns.SRC_ID.getName()).append(", ").append(Columns.NAME.getName()).append(", ").append(Columns.PHONE.getName()).append(", ").append(Columns.SEX.getName()).append(", ").append(" ) VALUES (?, ?, ?, ?, ?)").toString();
        }

        static void bindValuesInBulkInsert(SQLiteStatement stmt, ContentValues values) {
            int i = 1;
            String value;
            stmt.bindLong(i++, values.getAsLong(Columns.ID.getName()));
            stmt.bindLong(i++, values.getAsLong(Columns.SRC_ID.getName()));
            value = values.getAsString(Columns.NAME.getName());
            stmt.bindString(i++, value != null ? value : "");
            value = values.getAsString(Columns.PHONE.getName());
            stmt.bindString(i++, value != null ? value : "");
            value = values.getAsString(Columns.SEX.getName());
            stmt.bindString(i++, value != null ? value : "");
        }
    }

    /**
     * Created in version 1
     */
    public static final class src extends TestContent {

        private static final String LOG_TAG = src.class.getSimpleName();

        public static final String TABLE_NAME = "src";
        public static final String TYPE_ELEM_TYPE = "vnd.android.cursor.item/-src";
        public static final String TYPE_DIR_TYPE = "vnd.android.cursor.dir/-src";

        public static final Uri CONTENT_URI = Uri.parse(TestContent.CONTENT_URI + "/" + TABLE_NAME);

        public static enum Columns implements ColumnMetadata {
            ID("id", "integer"),
            PSRC_ID("psrc_id", "integer"),
            NAME("name", "text");

            private final String mName;
            private final String mType;

            private Columns(String name, String type) {
                mName = name;
                mType = type;
            }

            @Override
            public int getIndex() {
                return ordinal();
            }

            @Override
            public String getName() {
                return mName;
            }

            @Override
            public String getType() {
                return mType;
            }
        }

        public static final String[] PROJECTION = new String[] {
                Columns.ID.getName(),
                Columns.PSRC_ID.getName(),
                Columns.NAME.getName()
        };

        private src() {
            // No private constructor
        }

        public static void createTable(SQLiteDatabase db) {
            if (TestProvider.ACTIVATE_ALL_LOGS) {
                Log.d(LOG_TAG, "src | createTable start");
            }
            /*db.execSQL("CREATE TABLE " + TABLE_NAME + " (" + Columns.ID.getName() + " " + Columns.ID.getType() + ", " + Columns.PDEPT_ID.getName() + " " + Columns.PDEPT_ID.getType() + ", " + Columns.STATUS.getName() + " " + Columns.STATUS.getType() + ", " + Columns.CHS_NAME.getName() + " " + Columns.CHS_NAME.getType() + ", " + Columns.LAYER.getName() + " " + Columns.LAYER.getType() + ", " + Columns.DEPT_SORT.getName() + " " + Columns.DEPT_SORT.getType() + ", " + Columns.WQP_CODE.getName() + " " + Columns.WQP_CODE.getType() + ", " + Columns.STAFF_COUNT.getName() + " " + Columns.STAFF_COUNT.getType() + ", PRIMARY KEY (" + Columns.ID.getName() + ")" + ");");

            if (TestProvider.ACTIVATE_ALL_LOGS) {
                Log.d(LOG_TAG, "src | createTable end");
            }*/

            //DBHelper.getInstance().formeDb();
        }

        // Version 1 : Creation of the table
        public static void upgradeTable(SQLiteDatabase db, int oldVersion, int newVersion) {
            if (TestProvider.ACTIVATE_ALL_LOGS) {
                Log.d(LOG_TAG, "src | upgradeTable start");
            }

            if (oldVersion < 1) {
                Log.i(LOG_TAG, "Upgrading from version " + oldVersion + " to " + newVersion
                        + ", data will be lost!");

                db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME + ";");
                createTable(db);
                return;
            }


            if (oldVersion != newVersion) {
                throw new IllegalStateException("Error upgrading the database to version "
                        + newVersion);
            }

            if (TestProvider.ACTIVATE_ALL_LOGS) {
                Log.d(LOG_TAG, "src | upgradeTable end");
            }
        }

        static String getBulkInsertString() {
            return new StringBuilder("INSERT INTO ").append(TABLE_NAME).append(" ( ").append(Columns.ID.getName()).append(", ").append(Columns.PSRC_ID.getName()).append(", ").append(Columns.NAME.getName()).append(", ").append(" ) VALUES (?, ?, ?)").toString();
        }

        static void bindValuesInBulkInsert(SQLiteStatement stmt, ContentValues values) {
            int i = 1;
            String value;
            stmt.bindLong(i++, values.getAsLong(Columns.ID.getName()));
            stmt.bindLong(i++, values.getAsLong(Columns.PSRC_ID.getName()));
            value = values.getAsString(Columns.NAME.getName());
            stmt.bindString(i++, value != null ? value : "");
        }
    }
}

