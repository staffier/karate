/*
 * The MIT License
 *
 * Copyright 2020 Intuit Inc.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package com.intuit.karate.match;

/**
 *
 * @author pthomas3
 */
public class MatchContext {

    public final MatchOperation root;
    public final int depth;
    public final boolean xml;
    public final String path;
    public final String name;
    public final int index;

    protected MatchContext(MatchOperation root, boolean xml, int depth, String path, String name, int index) {
        this.root = root;
        this.xml = xml;
        this.depth = depth;
        this.path = path;
        this.name = name;
        this.index = index;
    }

    public MatchContext descend(String name) {
        if (xml) {
            String childPath = path.endsWith("/@") ? path + name : (depth == 0 ? "" : path) + "/" + name;
            return new MatchContext(root, xml, depth + 1, childPath, name, -1);
        } else {
            boolean needsQuotes = name.indexOf('-') != -1 || name.indexOf(' ') != -1 || name.indexOf('.') != -1;
            String childPath = needsQuotes ? path + "['" + name + "']" : path + '.' + name;
            return new MatchContext(root, xml, depth + 1, childPath, name, -1);
        }
    }

    public MatchContext descend(int index) {
        if (xml) {
            return new MatchContext(root, xml, depth + 1, path + "[" + (index + 1) + "]", name, index);
        } else {
            return new MatchContext(root, xml, depth + 1, path + "[" + index + "]", name, index);
        }
    }

}