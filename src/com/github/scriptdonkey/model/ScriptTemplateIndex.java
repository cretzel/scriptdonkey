package com.github.scriptdonkey.model;

import java.io.Serializable;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.github.scriptdonkey.persistence.PMF;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.repackaged.com.google.common.collect.Lists;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class ScriptTemplateIndex implements Serializable {

    private static final long serialVersionUID = 1L;

    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;

    @Persistent
    private String keyword;

    @Persistent
    private List<Key> scriptTemplateKeys = Lists.newArrayList();

    public ScriptTemplateIndex() {
    }

    public Key getKey() {
        return key;
    }

    public void setKey(final Key key) {
        this.key = key;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(final String keyword) {
        this.keyword = keyword;
    }

    public List<Key> getScriptTemplateKeys() {
        return scriptTemplateKeys;
    }

    public void setScriptTemplateKeys(final List<Key> scriptTemplateKeys) {
        this.scriptTemplateKeys = scriptTemplateKeys;
    }

    public void save() {
        final PersistenceManager pm = getPersistenceManager();
        try {
            pm.makePersistent(this);
        } finally {
            pm.close();
        }
    }

    public static ScriptTemplateIndex get(final Key key) {
        final PersistenceManager pm = getPersistenceManager();
        try {
            return pm.getObjectById(ScriptTemplateIndex.class, key);
        } finally {
            pm.close();
        }
    }

    @SuppressWarnings("unchecked")
    public static ScriptTemplateIndex find(final String keyword) {
        final PersistenceManager pm = getPersistenceManager();
        try {
            final Query query = pm.newQuery(ScriptTemplateIndex.class);
            query.setFilter("this.keyword == k");
            query.declareParameters("String k");
            final List<ScriptTemplateIndex> result = (List<ScriptTemplateIndex>) query
                    .execute(keyword);
            if (result.isEmpty()) {
                return null;
            } else {
                return result.get(0);
            }
        } finally {
            pm.close();
        }
    }

    public static int size() {
        final PersistenceManager pm = getPersistenceManager();
        try {
            final Query query = pm.newQuery(ScriptTemplateIndex.class);
            query.setResult("count(key)");
            return (Integer) query.execute();
        } finally {
            pm.close();
        }
    }

    private static PersistenceManager getPersistenceManager() {
        return PMF.get().getPersistenceManager();
    }

}
