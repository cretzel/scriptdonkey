package com.github.scriptdonkey.model;

import java.util.List;
import java.util.StringTokenizer;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.repackaged.com.google.common.base.StringUtil;
import com.google.appengine.repackaged.com.google.common.collect.Lists;

public class Indexer {

    public static void index(final ScriptTemplate scriptTemplate) {
        removeFromIndex(scriptTemplate);
        addToIndex(scriptTemplate);
    }

    public static void unindex(final ScriptTemplate scriptTemplate) {
        removeFromIndex(scriptTemplate);
    }

    private static void addToIndex(final ScriptTemplate scriptTemplate) {
        final List<String> keywords = Lists.newArrayList();

        final String title = scriptTemplate.getTitle();
        final List<String> tokenized = tokenize(title);
        keywords.addAll(tokenized);

        final String script = scriptTemplate.getScript();
        final List<String> tokenizedScript = tokenize(script);
        keywords.addAll(tokenizedScript);

        for (final String keyword : keywords) {
            ScriptTemplateIndex index = ScriptTemplateIndex.find(keyword);
            if (index == null) {
                index = new ScriptTemplateIndex();
                index.setKeyword(keyword);
            }

            final List<Key> scriptTemplateKeys = index.getScriptTemplateKeys();
            if (!scriptTemplateKeys.contains(scriptTemplate.getKey())) {
                scriptTemplateKeys.add(scriptTemplate.getKey());
                index.save();
                scriptTemplate.getIndexKeys().add(index.getKey());
                scriptTemplate.save();
            }
        }
    }

    private static void removeFromIndex(final ScriptTemplate scriptTemplate) {
        final Key scriptTemplateKey = scriptTemplate.getKey();
        final List<Key> currentIndexes = scriptTemplate.getIndexKeys();
        for (final Key indexKey : currentIndexes) {
            final ScriptTemplateIndex index = ScriptTemplateIndex.get(indexKey);
            if (index != null) {
                final List<Key> indexedTemplates = index
                        .getScriptTemplateKeys();
                while (indexedTemplates.contains(scriptTemplateKey)) {
                    indexedTemplates.remove(scriptTemplateKey);
                }
            }
            index.save();
        }
        scriptTemplate.getIndexKeys().clear();
        scriptTemplate.save();
    }

    public static List<String> tokenize(final String string) {
        final String cleanString = string.replaceAll("[^a-zA-Z]", " ");
        final StringTokenizer tokenizer = new StringTokenizer(cleanString);
        final List<String> tokens = Lists.newArrayList();
        while (tokenizer.hasMoreTokens()) {
            String nextToken = tokenizer.nextToken();
            nextToken = StringUtil.toLowerCase(nextToken).trim();
            tokens.add(nextToken);
        }
        return tokens;
    }

}
