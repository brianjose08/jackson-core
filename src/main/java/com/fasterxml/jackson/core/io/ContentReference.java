package com.fasterxml.jackson.core.io;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Abstraction that encloses information about content being processed --
 * input source or output targe, streaming or
 * not -- for the purpose of including pertinent information in
 * location (see {@link com.fasterxml.jackson.core.JsonLocation})
 * objections, most commonly to be printed out as part of {@code Exception}
 * messages.
 *
 * @since 2.13
 */
public class ContentReference
    // sort of: we will read back as "UNKNOWN_INPUT"
    implements java.io.Serializable
{
    private static final long serialVersionUID = 1L;

    protected final static ContentReference UNKNOWN_CONTENT =
            new ContentReference(false, null);

    /**
     * Reference to the actual underlying source.
     */
    protected final transient Object _rawContent;

    /**
     * For static input sources, indicates offset from the beginning
     * of static array.
     * {@code -1} if not in use.
     */
    protected final int _offset;

    /**
     * For static input sources, indicates length of content in
     * the static array.
     * {@code -1} if not in use.
     */
    protected final int _length;

    /**
     * Marker flag to indicate whether included content is textual or not:
     * this is taken to mean, by default, that a snippet of content may be
     * displayed for exception messages. 
     */
    protected final boolean _isContentTextual;

    /*
    /**********************************************************************
    /* Life-cycle
    /**********************************************************************
     */

    public ContentReference(boolean isContentTextual, Object rawContent) {
        this(isContentTextual, rawContent, -1, -1);
    }

    public ContentReference(boolean isContentTextual, Object rawContent,
            int offset, int length)
    {
        _isContentTextual = isContentTextual;
        _rawContent = rawContent;
        _offset = offset;
        _length = length;
    }

    /**
     * Accessor for getting a placeholder for cases where actual input source
     * is not known (or is not something that system wants to expose).
     *
     * @return Placeholder "unknown" (or "empty") instance to use instead of
     *    {@code null} reference
     */
    public static ContentReference unknown() {
        return UNKNOWN_CONTENT;
    }

    /**
     * Factory method for legacy code to use for constructing instances to
     * input sources for which only minimal amount of information is available.
     * Assumed not to contain textual content (no snippet displayed).
     * 
     * @param rawContent Underlying raw content access
     *
     * @return Instance with minimal information about content (basically just
     *    raw content reference without offsets; 
     */
    public static ContentReference rawReference(Object rawContent) {
        // 14-Mar-2021, tatu: Just to avoid russian-doll-nesting, let's:
        if (rawContent instanceof ContentReference) {
            return (ContentReference) rawContent;
        }
        return new ContentReference(false, rawContent);
    }

    /*
    /**********************************************************************
    /* Serializable overrides
    /**********************************************************************
     */    
    
    // For JDK serialization: can/should not retain raw source, so need
    // not read or write anything

    private void readObject(ObjectInputStream in) throws IOException {
        // nop: but must override the method
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        // nop: but must override the method
    }    

    protected Object readResolve() {
        return UNKNOWN_CONTENT;
    }    

    /*
    /**********************************************************************
    /* Basic accessors
    /**********************************************************************
     */    

    public boolean hasTextualContent() {
        return _isContentTextual;
    }

    public Object getSource() {
        return _rawContent;
    }

    /*
    /**********************************************************************
    /* Standard method overrides
    /**********************************************************************
     */    

    // Just needed for JsonLocation#equals(): although it'd seem we only need
    // to care about identity, for backwards compatibility better compare
    // bit more
    @Override
    public boolean equals(Object other)
    {
        if (other == this) return true;
        if (other == null) return false;
        if (!(other instanceof ContentReference)) return false;
        ContentReference otherSrc = (ContentReference) other;

        return _rawContent == otherSrc._rawContent;
    }
}