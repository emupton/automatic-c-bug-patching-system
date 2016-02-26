package scanBuildParser;

public enum BugIdentifier {
	RESOURCE_LEAK("resourceLeak");
	
	String cppCheckId;
	
	BugIdentifier(String cppCheckId ){
		this.cppCheckId = cppCheckId;
	}
	
	public String getCppCheckId(){
		return cppCheckId;
	}
	
	public static BugIdentifier getBugIdentifierByCppCheckId(String cppCheckId){
		for(BugIdentifier bug : BugIdentifier.values()){
			if(bug.getCppCheckId().equals(cppCheckId)){
				return bug;
			}
		}
		return null;
	}
}
