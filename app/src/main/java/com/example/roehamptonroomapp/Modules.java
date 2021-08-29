package com.example.roehamptonroomapp;

public class Modules {

    private String module_code, module_name,module_short, module_room, module_course;

    public Modules(String module_code, String module_name, String module_short, String module_room, String module_course) {
        this.module_code = module_code;
        this.module_name = module_name;
        this.module_short = module_short;
        this.module_room = module_room;
        this.module_course = module_course;
    }

    public Modules() {
    }

    public String getModule_code() {
        return module_code;
    }

    public void setModule_code(String module_code) {
        this.module_code = module_code;
    }

    public String getModule_name() {
        return module_name;
    }

    public void setModule_name(String module_name) {
        this.module_name = module_name;
    }

    public String getModule_short() {
        return module_short;
    }

    public void setModule_short(String module_short) {
        this.module_short = module_short;
    }

    public String getModule_room() {
        return module_room;
    }

    public void setModule_room(String module_room) {
        this.module_room = module_room;
    }

    public String getModule_course() {
        return module_course;
    }
}

//    private DatabaseReference reference;
//        reference = FirebaseDatabase.getInstance().getReference("_module");
//
//        Modules module1 = new Modules("QAC020N251S", "Mobile App Design and Development",
//                "MADD", "G02", "Computing Technology Degree");
//
//        Modules module2 = new Modules("QAC020N252S", "User Experience Design",
//                "UED", "LG104", "Computing Technology Degree");
//
//        Modules module3 = new Modules("QAC020N253S", "Network Design and Troubleshooting",
//                "NDaT", "G01", "Computing Technology Degree");
//
//        Modules module4 = new Modules("QAC020C154H", "System Analysis and Design",
//                "SAD", "G02", "Computing Technology Degree");
//
//        Modules module5 = new Modules("QAC020C152S", "Object Oriented Programming",
//                "OOP", "LG102", "Computing Technology Degree");
//
//        Modules module6 = new Modules("QAC020C153S", "Web Development",
//                "WD", "G01", "Computing Technology Degree");
//
//        Modules module7 = new Modules("QAC021N251S", "Business Statistics",
//                "BS", "202", "Business And Management Degree");
//
//        Modules module8 = new Modules("QAC021N254S", "Economic Principles for Business and Markets",
//                "EPBM", "201", "Business And Management Degree");
//
//        Modules module9 = new Modules("QAC021N255S", "Fundamentals of Marketing",
//                "FM", "201", "Business And Management Degree");
//
//        Modules module10 = new Modules("QAC021N256S", "International Business Environment",
//                "IBE", "202", "Business And Management Degree");
//
//        Modules module11 = new Modules("QAC021N257S", "Introduction to Accounting and Finance",
//                "IAF", "202", "Business And Management Degree");
//
//        Modules module12 = new Modules("QAC021N258S", "Organisations and Management",
//                "OM", "204", "Business And Management Degree");
//
//        Modules module13 = new Modules("QAC023N251S", "Environmental Design",
//                "ED", "102", "Architecture Degree");
//
//        Modules module14 = new Modules("QAC023N253S", "Structural Design",
//                "SD", "102", "Architecture Degree");
//
//        Modules module15 = new Modules("QAC023N257S", "History & Theory of Architecture",
//                "HTA", "104", "Architecture Degree");
//
//        Modules module16 = new Modules("QAC023N259S", "Work Experience Module",
//                "WEM", "103", "Architecture Degree");
//
//        Modules module17 = new Modules("QAC023N262S", "Practice Management",
//                "PM", "104", "Architecture Degree");
//
//        Modules module18 = new Modules("QAC023N252S", "Urban Studies",
//                "US", "104", "Architecture Degree");
//
//        Modules module19 = new Modules("QAC024N253S", "Environmental Design",
//                "ED", "304", "Architecture Extended Degree");
//
//        Modules module20 = new Modules("QAC024N254S", "Structural Design",
//                "SD", "302", "Architecture Extended Degree");
//
//        Modules module21 = new Modules("QAC024N255S", "History & Theory of Architecture",
//                "HTA", "301", "Architecture Extended Degree");
//
//        Modules module22 = new Modules("QAC024N256S", "Work Experience Module",
//                "WEM", "304", "Architecture Extended Degree");
//
//        Modules module23 = new Modules("QAC024N257S", "Practice Management",
//                "PM", "302", "Architecture Extended Degree");
//
//        Modules module24 = new Modules("QAC024N258S", "Urban Studies",
//                "US", "304", "Architecture Extended Degree");
//
//        Modules module25 = new Modules("QAC040N250S", "Anatomy and Human Biology",
//                "AHB", "LG2A", "Biochemistry Degree");
//
//        Modules module26 = new Modules("QAC040N251S", "Biochemistry",
//                "Bio", "103", "Biochemistry Degree");
//
//        Modules module27 = new Modules("QAC040N253S", "Biological and Medical Sciences",
//                "BMS", "103", "Biochemistry Degree");
//
//        Modules module28 = new Modules("QAC040N255S", "Microbiology",
//                "Mic", "105", "Biochemistry Degree");
//
//        Modules module29 = new Modules("QAC040N256S", "Human Physiology",
//                "HP", "LG102", "Biochemistry Degree");
//
//        Modules module30 = new Modules("QAC040N258S", "Tropical Disease",
//                "TD", "202", "Biochemistry Degree");
//
//
//        reference.child(reference.push().getKey()).setValue(module1);
//        reference.child(reference.push().getKey()).setValue(module2);
//        reference.child(reference.push().getKey()).setValue(module3);
//        reference.child(reference.push().getKey()).setValue(module4);
//        reference.child(reference.push().getKey()).setValue(module5);
//        reference.child(reference.push().getKey()).setValue(module6);
//        reference.child(reference.push().getKey()).setValue(module7);
//        reference.child(reference.push().getKey()).setValue(module8);
//        reference.child(reference.push().getKey()).setValue(module9);
//        reference.child(reference.push().getKey()).setValue(module10);
//        reference.child(reference.push().getKey()).setValue(module11);
//        reference.child(reference.push().getKey()).setValue(module12);
//        reference.child(reference.push().getKey()).setValue(module13);
//        reference.child(reference.push().getKey()).setValue(module14);
//        reference.child(reference.push().getKey()).setValue(module15);
//        reference.child(reference.push().getKey()).setValue(module16);
//        reference.child(reference.push().getKey()).setValue(module17);
//        reference.child(reference.push().getKey()).setValue(module18);
//        reference.child(reference.push().getKey()).setValue(module19);
//        reference.child(reference.push().getKey()).setValue(module20);
//        reference.child(reference.push().getKey()).setValue(module21);
//        reference.child(reference.push().getKey()).setValue(module22);
//        reference.child(reference.push().getKey()).setValue(module23);
//        reference.child(reference.push().getKey()).setValue(module24);
//        reference.child(reference.push().getKey()).setValue(module25);
//        reference.child(reference.push().getKey()).setValue(module26);
//        reference.child(reference.push().getKey()).setValue(module27);
//        reference.child(reference.push().getKey()).setValue(module28);
//        reference.child(reference.push().getKey()).setValue(module29);
//        reference.child(reference.push().getKey()).setValue(module30)