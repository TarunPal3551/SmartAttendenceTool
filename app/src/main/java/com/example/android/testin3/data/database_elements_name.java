/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.testin3.data;

import android.provider.BaseColumns;

/**
 * API Contract for the Pets app.
 */
public final class database_elements_name {

    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    private database_elements_name() {}


//TO access this below string just use database_elements_name.DATABASE_NAME;
//NAME OF THE DATABASE
    public final static String DATABASE_NAME = "Present_app.db";
    //NAME OF THE other DATABASE
    public final static String DATABASE_NAME_2 = "database_2.db";

    /**
     * Inner class that defines constant values for the variable_table database table.
     * Each entry in the table represents a single variable.
     */


    //this class is for variables table .....for another table make another class
    public static final class variables_entry implements BaseColumns {


//use the code -variables_entry.TABLE_NAME to access the string of table name and others
        /** Name of database table for pets */
        public final static String TABLE_NAME = "variables_table";

        /**
         * Unique ID number for the variables (only for use in the database table).
         *
         * Type: INTEGER
         */
        public final static String COLUMN_1_ID = "ID";

        /**
         * Type: TEXT
         */
        public final static String COLUMN_2_VARIABLE_NAME ="name";

        /**
         * Type: INTEGER
         */
        public final static String COLUMN_3_VALUE_OR_STATUS = "value";

        /**
         * Possible values for the COLUMN_VALUE_OR_STATUS or ANY ELEMNTS(ROW) of the VARIABLES TABLE.
         */
        //MODIFIED THE BELOW ONES LATER

        public static final int GENDER_UNKNOWN = 0;
        public static final int GENDER_MALE = 1;
        public static final int GENDER_FEMALE = 2;
    }
   // -----------------------------------------------------------------------------------------------------------------------------------------------------------------------
//this class is for variables table .....for another table make another class
    public static final class checkboxes_table implements BaseColumns {


//use the code -variables_entry.TABLE_NAME to access the string of table name and others
        /** Name of database table for pets */
        public final static String TABLE_NAME_2 = "checkboxes_table";

        /**
         * Unique ID number for the variables (only for use in the database table).
         *
         * Type: INTEGER
         */
        //public final static String COLUMN_1_ID = "ID"; //we are not giving id column to checkboxes table because there is only will be one element(row) always so no need of primary keey column


         // Type: integer
        public final static String COLUMN_0_Monday ="mon";
        //  Type: INTEGER
        public final static String COLUMN_1_tuesday = "tue";
       //  Type: INTEGER
       public final static String COLUMN_2_wednesday = "wed";
       //  Type: INTEGER
       public final static String COLUMN_3_thursday = "thu";
       //  Type: INTEGER
       public final static String COLUMN_4_friday = "fri";
       //  Type: INTEGER
       public final static String COLUMN_5_saturday = "sat";
       //  Type: INTEGER
       public final static String COLUMN_6_sunday = "sun";


        /**
         * Possible values for the COLUMN_VALUE_OR_STATUS or ANY ELEMNTS(ROW) of the VARIABLES TABLE.
         */
        //MODIFIED THE BELOW ONES LATER

       // public static final int GENDER_UNKNOWN = 0;
        //public static final int GENDER_MALE = 1;
        //public static final int GENDER_FEMALE = 2;
    }
    // -----------------------------------------------------------------------------------------------------------------------------------------------------------------------
//this class is for variables table .....for another table make another class
    public static final class table_of_data_of_a_pericular_week implements BaseColumns {


//use the code -variables_entry.TABLE_NAME to access the string of table name and others
        /**
         * Name of database table for pets
         */
       // public final static String TABLE_NAME_3 = "week_no_data_table";

        /**
         * Unique ID number for the variables (only for use in the database table).
         * <p>
         * Type: INTEGER
         */
        public final static String COLUMN_1_ID = "ID";

        // Type: string
        public final static String COLUMN_0_Monday = "mon";
        //  Type: string
        public final static String COLUMN_1_tuesday = "tue";
        //  Type: string
        public final static String COLUMN_2_wednesday = "wed";
        //  Type: string
        public final static String COLUMN_3_thursday = "thu";
        //  Type: string
        public final static String COLUMN_4_friday = "fri";
        //  Type: string
        public final static String COLUMN_5_saturday = "sat";
        //  Type: string
        public final static String COLUMN_6_sunday = "sun";

    }
}

