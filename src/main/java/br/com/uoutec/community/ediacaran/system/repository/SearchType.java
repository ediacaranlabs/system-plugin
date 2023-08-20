package br.com.uoutec.community.ediacaran.system.repository;

import java.util.Locale;

public enum SearchType implements Filter {

	
	REGEX(){
		
		@Override
		public boolean accept(String path, String name,Locale locale, boolean recursive, ObjectMetadata omd) {
			
			if(path != null) {
				if(recursive) {
					if(!omd.getPathMetadata().getPath().startsWith(path)) {
						return false;
					}
				}
				else
				if(!omd.getPathMetadata().getPath().equals(path)) {
					return false;
				}
			}
			
			if(locale != null) {
				if(!omd.getLocale().equals(locale)) {
					return false;
				}
			}


			return name == null || omd.getPathMetadata().getId().matches(name);
		}
		
	},
	
	EQUAL(){
		
		@Override
		public boolean accept(String path, String name, Locale locale, boolean recursive, ObjectMetadata omd) {
			
			if(path != null) {
				if(recursive) {
					if(!omd.getPathMetadata().getPath().startsWith(path)) {
						return false;
					}
				}
				else
				if(!omd.getPathMetadata().getPath().equals(path)) {
					return false;
				}
			}
			
			if(locale != null) {
				if(!omd.getLocale().equals(locale)) {
					return false;
				}
			}


			return name == null || omd.getPathMetadata().getId().equals(name);
		}
		
	},

	EQUAL_LOCALE(){
		
		@Override
		public boolean accept(String path, String name,Locale locale, boolean recursive, ObjectMetadata omd) {
			
			if(path != null) {
				if(recursive) {
					if(!omd.getPathMetadata().getPath().startsWith(path)) {
						return false;
					}
				}
				else
				if(!omd.getPathMetadata().getPath().equals(path)) {
					return false;
				}
			}
			
			if(locale == null){
				if(omd.getLocale() != null) {
					return false;
				}
			}
			else
			if(!locale.equals(omd.getLocale())){
				return false;
			}
			
			return name == null || omd.getPathMetadata().getId().equals(name);
		}
		
	},
	
	CONTAINS(){
		
		@Override
		public boolean accept(String path, String name,Locale locale, boolean recursive, ObjectMetadata omd) {
			
			if(path != null) {
				if(recursive) {
					if(!omd.getPathMetadata().getPath().startsWith(path)) {
						return false;
					}
				}
				else
				if(!omd.getPathMetadata().getPath().equals(path)) {
					return false;
				}
			}
			
			if(locale != null) {
				if(!omd.getLocale().equals(locale)) {
					return false;
				}
			}


			return name == null || omd.getPathMetadata().getId().contains(name);
		}
		
	},
	
	NOT_EQUAL(){
		
		@Override
		public boolean accept(String path, String name,Locale locale, boolean recursive, ObjectMetadata omd) {
			
			if(path != null) {
				if(recursive) {
					if(!omd.getPathMetadata().getPath().startsWith(path)) {
						return false;
					}
				}
				else
				if(!omd.getPathMetadata().getPath().equals(path)) {
					return false;
				}
			}
			
			if(locale != null) {
				if(!omd.getLocale().equals(locale)) {
					return false;
				}
			}


			return name == null || !omd.getPathMetadata().getId().equals(name);
		}
		
	},
	
	NOT_CONTAINS(){
		
		@Override
		public boolean accept(String path, String name,Locale locale, boolean recursive, ObjectMetadata omd) {
			
			if(path != null) {
				if(recursive) {
					if(!omd.getPathMetadata().getPath().startsWith(path)) {
						return false;
					}
				}
				else
				if(!omd.getPathMetadata().getPath().equals(path)) {
					return false;
				}
			}
			
			if(locale != null) {
				if(!omd.getLocale().equals(locale)) {
					return false;
				}
			}


			return name == null || !omd.getPathMetadata().getId().contains(name);
		}
		
	};

	@Override
	public boolean accept(String path, String name,Locale locale, boolean recursive, ObjectMetadata omd) {
		return false;
	}
	
}
