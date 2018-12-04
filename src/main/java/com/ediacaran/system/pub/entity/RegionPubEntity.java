package com.ediacaran.system.pub.entity;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.ediacaran.system.entity.Region;
import com.ediacaran.system.pub.entity.SystemUserPubEntity.DataValidatorGroup;

import br.com.uoutec.application.validation.CommonValidation;
import br.com.uoutec.pub.entity.AbstractPubEntity;
import br.com.uoutec.pub.entity.DataValidation;
import br.com.uoutec.pub.entity.IdValidation;

public class RegionPubEntity extends AbstractPubEntity<Region>{

	private static final long serialVersionUID = 5018197076890348284L;

	@NotNull(groups = IdValidation.class)
	private String id;

	private String parent;

	@NotNull(groups={DataValidatorGroup.class, DataValidation.class})
	@Pattern(regexp=CommonValidation.NAME_FORMAT, groups={DataValidatorGroup.class, DataValidation.class})
	private String name;

	@NotNull
	private LanguagePubEntity language;

	@NotNull(groups={DataValidatorGroup.class, DataValidation.class})
	@Size(min=2,max=12, groups={DataValidatorGroup.class, DataValidation.class})
	private String alphabet;
	
	public RegionPubEntity(){
	}
	
	public RegionPubEntity(Region e){
		this.alphabet = e.getAlphabet();
		this.id = e.getId();
		this.language = e.getLanguage() == null? null :  new LanguagePubEntity(e.getLanguage());
		this.name = e.getName();
		this.parent = e.getParent();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LanguagePubEntity getLanguage() {
		return language;
	}

	public void setLanguage(LanguagePubEntity language) {
		this.language = language;
	}

	public String getAlphabet() {
		return alphabet;
	}

	public void setAlphabet(String alphabet) {
		this.alphabet = alphabet;
	}

	@Override
	protected boolean isEqualId(Region instance) throws Throwable {
		return this.id == null? instance.getId() == null : instance.getId().equals(this.id);
	}

	@Override
	protected boolean hasId(Region instance) throws Throwable {
		return instance.getId() != null;
	}

	@Override
	protected Region reloadEntity() throws Throwable {
		Region e = new Region();
		e.setId(this.id);
		return e;
	}

	@Override
	protected void throwReloadEntityFail() throws Throwable {
		throw new UnsupportedOperationException();
	}

	@Override
	protected Region createNewInstance() throws Throwable {
		return new Region();
	}

	@Override
	protected void copyTo(Region o, boolean reload, boolean override,
			boolean validate) throws Throwable {
		o.setAlphabet(this.alphabet);
		o.setId(this.id);
		o.setLanguage(this.language == null? null : this.language.rebuild(reload,override,validate));
		o.setName(this.name);
		o.setParent(this.parent);
	}
	
}
