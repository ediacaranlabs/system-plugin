package com.ediacaran.system.pub.entity;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.ediacaran.system.entity.Language;
import com.ediacaran.system.pub.entity.SystemUserPubEntity.DataValidatorGroup;

import br.com.uoutec.application.validation.CommonValidation;
import br.com.uoutec.pub.entity.AbstractPubEntity;
import br.com.uoutec.pub.entity.DataValidation;
import br.com.uoutec.pub.entity.IdValidation;

public class LanguagePubEntity extends AbstractPubEntity<Language>{

	private static final long serialVersionUID = 1564544377135683753L;

	@NotNull(groups = IdValidation.class)
	private Integer id;

	@NotNull(groups={DataValidatorGroup.class, DataValidation.class})
	@Size(min=2,max=12, groups={DataValidatorGroup.class, DataValidation.class})
	private String iso6391;

	@NotNull(groups={DataValidatorGroup.class, DataValidation.class})
	@Size(min=2,max=12, groups={DataValidatorGroup.class, DataValidation.class})
	private String iso6392t;
	
	@NotNull(groups={DataValidatorGroup.class, DataValidation.class})
	@Pattern(regexp=CommonValidation.NAME_FORMAT, groups={DataValidatorGroup.class, DataValidation.class})
	private String name;

	@NotNull(groups={DataValidatorGroup.class, DataValidation.class})
	@Pattern(regexp=CommonValidation.NAME_FORMAT, groups={DataValidatorGroup.class, DataValidation.class})
	private String isoName;
	
	public LanguagePubEntity(){
	}
	
	public LanguagePubEntity(Language e){
		this.id = e.getId();
		this.iso6392t = e.getIso6392t();
		this.iso6391 = e.getIso6391();
		this.name = e.getName();
		this.isoName = e.getIsoName();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getIso6391() {
		return iso6391;
	}

	public void setIso6391(String iso6391) {
		this.iso6391 = iso6391;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	protected boolean isEqualId(Language instance) throws Throwable {
		return this.id == null? instance.getId() <= 0 : instance.getId() == this.id;
	}

	@Override
	protected boolean hasId(Language instance) throws Throwable {
		return instance.getId() >= 0;
	}

	@Override
	protected Language reloadEntity() throws Throwable {
		Language e = new Language();
		e.setId(this.id);
		return e;
	}

	@Override
	protected void throwReloadEntityFail() throws Throwable {
		throw new UnsupportedOperationException();
	}

	@Override
	protected Language createNewInstance() throws Throwable {
		return new Language();
	}

	@Override
	protected void copyTo(Language o, boolean reload, boolean override,
			boolean validate) throws Throwable {
		o.setId(this.id);
		o.setIso6392t(this.iso6392t);
		o.setIso6391(this.iso6391);
		o.setName(this.name);
		o.setName(this.isoName);
	}
	
}
