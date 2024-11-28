package __Init__;

class Convertables {
	
	protected String Types(String javaType) {
        
        if (javaType.toUpperCase().contains("VARCHAR") || 
            javaType.toUpperCase().contains("INT") || 
            javaType.toUpperCase().contains("DOUBLE") || 
            javaType.toUpperCase().contains("BOOLEAN") || 
            javaType.toUpperCase().contains("DATE")) {
            return javaType;  
        }

       
        switch (javaType.toLowerCase()) {
            case "string":
                return "VARCHAR(255)";
            case "int":
                return "INT";
            case "double":
                return "DOUBLE";
            case "boolean":
                return "BOOLEAN";
            case "date":
                return "DATE";
            default:
                return "VARCHAR(255)";  
        }
    }
}
