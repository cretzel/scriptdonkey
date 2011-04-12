package com.github.scriptdonkey.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

import com.github.scriptdonkey.Users;
import com.github.scriptdonkey.persistence.PMF;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.users.User;
import com.google.appengine.repackaged.com.google.common.collect.Lists;
import com.google.appengine.repackaged.com.google.common.collect.Maps;
import com.google.appengine.repackaged.com.google.common.collect.Sets;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class ScriptTemplate implements Serializable {

    private static final long serialVersionUID = 1L;

    @PrimaryKey
    @Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
    private Key key;

    @Persistent
    private User user;

    @Persistent
    private String title;

    @Persistent
    private String script;

    @Persistent
    private Lang lang = Lang.GROOVY;

    @Persistent
    private Date created;

    @Persistent
    private Date modified;

    @Persistent
    private List<Key> indexKeys = Lists.newArrayList();

    public ScriptTemplate() {
        this.created = new Date();
        this.modified = created;
    }

    public ScriptTemplate(final String title, final String script,
            final Lang lang) {
        super();
        this.title = title;
        this.script = script;
        this.lang = lang;
        this.created = new Date();
        this.modified = created;
    }

    public ScriptTemplate(final String title, final String script,
            final Lang lang, final User user) {
        super();
        this.title = title;
        this.script = script;
        this.lang = lang;
        this.user = user;
        this.created = new Date();
        this.modified = created;
    }

    public User getUser() {
        return user;
    }

    public void setUser(final User user) {
        this.user = user;
    }

    public Key getKey() {
        return key;
    }

    public void setKey(final Key key) {
        this.key = key;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public String getScript() {
        return script;
    }

    public void setScript(final String script) {
        this.script = script;
    }

    public Lang getLang() {
        return lang;
    }

    public void setLang(final Lang lang) {
        this.lang = lang;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(final Date created) {
        this.created = created;
    }

    public Date getModified() {
        return modified;
    }

    public void setModified(final Date modified) {
        this.modified = modified;
    }

    public List<Key> getIndexKeys() {
        return indexKeys;
    }

    public void setIndexKeys(final List<Key> indexKeys) {
        this.indexKeys = indexKeys;
    }

    void saveAndIndex() {
        final PersistenceManager pm = getPersistenceManager();
        try {
            this.modified = new Date();
            pm.makePersistent(this);
        } finally {
            pm.close();
        }
        Indexer.index(this);
    }

    void save() {
        final PersistenceManager pm = getPersistenceManager();
        try {
            this.modified = new Date();
            pm.makePersistent(this);
        } finally {
            pm.close();
        }
    }

    public void save(final User user) {
        if (isUpdateAllowed(user)) {
            if (this.key == null) {
                this.user = user;
            }
            saveAndIndex();
        } else {
            throw new RuntimeException("not allowed");
        }

    }

    public static ScriptTemplate get(final Key key) {
        final PersistenceManager pm = getPersistenceManager();
        try {
            final ScriptTemplate objectById = pm.getObjectById(
                    ScriptTemplate.class, key);
            pm.detachCopy(objectById);
            return objectById;
        } finally {
            pm.close();
        }
    }

    public static int size() {
        final PersistenceManager pm = getPersistenceManager();
        try {
            final Query query = pm.newQuery(ScriptTemplate.class);
            query.setResult("count(key)");
            return (Integer) query.execute();
        } finally {
            pm.close();
        }
    }

    public static int size(final User user) {
        final PersistenceManager pm = getPersistenceManager();
        try {
            final Query query = pm.newQuery(ScriptTemplate.class);
            query.setResult("count(key)");
            query.declareImports("import com.google.appengine.api.users.User;");
            query.setFilter("this.user == u");
            query.declareParameters("User u");
            return (Integer) query.execute(user);
        } finally {
            pm.close();
        }
    }

    @SuppressWarnings("unchecked")
    public static List<ScriptTemplate> all(final int first, final int count) {
        final PersistenceManager pm = getPersistenceManager();
        try {
            final Query query = pm.newQuery(ScriptTemplate.class);
            query.setRange(first, first + count);
            final List<ScriptTemplate> result = (List<ScriptTemplate>) query
                    .execute();
            final List<ScriptTemplate> list = new ArrayList<ScriptTemplate>(
                    pm.detachCopyAll(result));
            return list;
        } finally {
            pm.close();
        }
    }

    @SuppressWarnings("unchecked")
    public static List<ScriptTemplate> all(final User user, final int first,
            final int count) {
        final PersistenceManager pm = getPersistenceManager();
        try {
            final Query query = pm.newQuery(ScriptTemplate.class);
            query.setRange(first, first + count);
            query.declareImports("import com.google.appengine.api.users.User;");
            query.setFilter("user == u");
            query.declareParameters("User u");

            final List<ScriptTemplate> result = (List<ScriptTemplate>) query
                    .execute(user);
            final List<ScriptTemplate> list = new ArrayList<ScriptTemplate>(
                    pm.detachCopyAll(result));
            return list;
        } finally {
            pm.close();
        }
    }

    public void delete(final User user) {
        if (isDeleteAllowed(user)) {
            Indexer.unindex(this);
            delete();
        } else {
            throw new RuntimeException("Not allowed");
        }
    }

    private void delete() {
        final PersistenceManager pm = getPersistenceManager();
        try {
            final Object object = pm.getObjectById(ScriptTemplate.class, key);
            pm.deletePersistent(object);
        } finally {
            pm.close();
        }
    }

    private static PersistenceManager getPersistenceManager() {
        return PMF.get().getPersistenceManager();
    }

    public boolean isDeleteAllowed(final User user) {
        final User owner = this.user;

        if (key == null) {
            return true;
        }
        if (owner == null) {
            return true;
        }
        if (owner != null && user == null) {
            return false;
        }
        if (owner != null && user != null) {
            return Users.equal(user, owner);
        }

        return false;
    }

    public boolean isUpdateAllowed(final User user) {
        final User owner = this.user;

        if (key == null) {
            return true;
        }
        if (owner == null) {
            return true;
        }
        if (owner != null && user == null) {
            return false;
        }
        if (owner != null && user != null) {
            return Users.equal(user, owner);
        }

        return false;
    }

    public static List<ScriptTemplate> search(final String keyword,
            final int first, final int count) {
        if (keyword == null || keyword.equals("")) {
            return Lists.newArrayList();
        }

        final ScriptTemplateIndex index = ScriptTemplateIndex.find(keyword
                .toLowerCase());
        if (index == null) {
            return Lists.newArrayList();
        }

        final List<Key> scriptTemplateKeys = index.getScriptTemplateKeys();
        final Map<Key, Integer> countMap = Maps.newHashMap();
        for (final Key key : scriptTemplateKeys) {
            if (!countMap.containsKey(key)) {
                countMap.put(key, 0);
            }
            final Integer keyCount = countMap.get(key);
            countMap.put(key, keyCount + 1);
        }

        final List<Entry<Key, Integer>> entryList = Lists.newArrayList(countMap
                .entrySet());
        Collections.sort(entryList, new Comparator<Entry<Key, Integer>>() {

            @Override
            public int compare(final Entry<Key, Integer> o1,
                    final Entry<Key, Integer> o2) {
                return o1.getValue().compareTo(o2.getValue());
            }
        });

        final List<ScriptTemplate> result = Lists.newArrayList();
        for (final Entry<Key, Integer> entry : entryList) {
            result.add(get(entry.getKey()));
        }
        return result.subList(first, Math.min(result.size(), first + count));
    }

    public static int searchSize(final String keyword) {
        if (keyword == null || keyword.equals("")) {
            return 0;
        }

        final ScriptTemplateIndex index = ScriptTemplateIndex.find(keyword
                .toLowerCase());
        if (index != null) {
            final List<Key> scriptTemplateKeys = index.getScriptTemplateKeys();
            final Set<Key> keySet = Sets.newHashSet(scriptTemplateKeys);
            return keySet.size();
        } else {
            return 0;
        }
    }

}
