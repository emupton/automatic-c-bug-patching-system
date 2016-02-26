
public enum BugIdentifier {
	RESOURCE_LEAK("resourceLeak"),
	MALLOC_MISMATCH("Result of 'malloc' is converted to a pointer of type");
	
	String cppCheckId;
	
	BugIdentifier(String cppCheckId ){
		this.cppCheckId = cppCheckId;
	}
	
	public String getCppCheckId(){
		return cppCheckId;
	}
	
	public static BugIdentifier getBugIdentifierByCppCheckId(String cppCheckId){
		for(BugIdentifier bug : BugIdentifier.values()){
			if(cppCheckId.contains(bug.getCppCheckId())){
				return bug;
			}
		}
		return null;
	}
}
