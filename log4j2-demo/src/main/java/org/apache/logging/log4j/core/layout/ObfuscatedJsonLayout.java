package org.apache.logging.log4j.core.layout;

import java.io.IOException;
import java.io.Writer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import org.apache.logging.log4j.core.Layout;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.DefaultConfiguration;
import org.apache.logging.log4j.core.config.Node;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.config.plugins.PluginBuilderAttribute;
import org.apache.logging.log4j.core.config.plugins.PluginBuilderFactory;
import org.apache.logging.log4j.core.config.plugins.PluginElement;
import org.apache.logging.log4j.core.layout.JsonLayout;
import org.apache.logging.log4j.core.layout.JsonLayout.Builder;
import org.apache.logging.log4j.core.util.KeyValuePair;

/**
 * @author dante on 2020-07-12.
 * @project log4j2-demo
 */
@Plugin(name = "ObfuscatedJsonLayout", category = Node.CATEGORY, elementType = Layout.ELEMENT_TYPE, printObject = true)
public class ObfuscatedJsonLayout extends AbstractJacksonLayout {

  private static final String DEFAULT_FOOTER = "]";

  private static final String DEFAULT_HEADER = "[";

  static final String CONTENT_TYPE = "application/json";

  public static class Builder<B extends ObfuscatedJsonLayout.Builder<B>> extends AbstractJacksonLayout.Builder<B>
      implements org.apache.logging.log4j.core.util.Builder<ObfuscatedJsonLayout> {

    @PluginBuilderAttribute
    private boolean propertiesAsList;

    @PluginBuilderAttribute
    private boolean objectMessageAsJsonObject;

    @PluginElement("AdditionalField")
    private KeyValuePair[] additionalFields;

    public Builder() {
      super();
      setCharset(StandardCharsets.UTF_8);
    }

    @Override
    public ObfuscatedJsonLayout build() {
      final boolean encodeThreadContextAsList = isProperties() && propertiesAsList;
      final String headerPattern = toStringOrNull(getHeader());
      final String footerPattern = toStringOrNull(getFooter());
      return new ObfuscatedJsonLayout(getConfiguration(), isLocationInfo(), isProperties(), encodeThreadContextAsList,
          isComplete(), isCompact(), getEventEol(), getEndOfLine(), headerPattern, footerPattern, getCharset(),
          isIncludeStacktrace(), isStacktraceAsString(), isIncludeNullDelimiter(), isIncludeTimeMillis(),
          getAdditionalFields(), getObjectMessageAsJsonObject());
    }

    public boolean isPropertiesAsList() {
      return propertiesAsList;
    }

    public B setPropertiesAsList(final boolean propertiesAsList) {
      this.propertiesAsList = propertiesAsList;
      return asBuilder();
    }

    public boolean getObjectMessageAsJsonObject() {
      return objectMessageAsJsonObject;
    }

    public B setObjectMessageAsJsonObject(final boolean objectMessageAsJsonObject) {
      this.objectMessageAsJsonObject = objectMessageAsJsonObject;
      return asBuilder();
    }

    @Override
    public KeyValuePair[] getAdditionalFields() {
      return additionalFields;
    }

    @Override
    public B setAdditionalFields(final KeyValuePair[] additionalFields) {
      this.additionalFields = additionalFields;
      return asBuilder();
    }
  }

  /**
   * @deprecated Use {@link #newBuilder()} instead
   */
  @Deprecated
  protected ObfuscatedJsonLayout(final Configuration config, final boolean locationInfo, final boolean properties,
      final boolean encodeThreadContextAsList,
      final boolean complete, final boolean compact, final boolean eventEol, final String endOfLine,final String headerPattern,
      final String footerPattern, final Charset charset, final boolean includeStacktrace) {
    super(config, new JacksonFactory.JSON(encodeThreadContextAsList, includeStacktrace, false, false).newWriter(
        locationInfo, properties, compact),
        charset, compact, complete, eventEol, endOfLine,
        PatternLayout.newSerializerBuilder().setConfiguration(config).setPattern(headerPattern).setDefaultPattern(DEFAULT_HEADER).build(),
        PatternLayout.newSerializerBuilder().setConfiguration(config).setPattern(footerPattern).setDefaultPattern(DEFAULT_FOOTER).build(),
        false, null);
  }

  private ObfuscatedJsonLayout(final Configuration config, final boolean locationInfo, final boolean properties,
      final boolean encodeThreadContextAsList,
      final boolean complete, final boolean compact, final boolean eventEol, final String endOfLine,
      final String headerPattern, final String footerPattern, final Charset charset,
      final boolean includeStacktrace, final boolean stacktraceAsString,
      final boolean includeNullDelimiter, final boolean includeTimeMillis,
      final KeyValuePair[] additionalFields, final boolean objectMessageAsJsonObject) {
    super(config, new JacksonFactory.JSON(encodeThreadContextAsList, includeStacktrace, stacktraceAsString, objectMessageAsJsonObject).newWriter(
        locationInfo, properties, compact, includeTimeMillis),
        charset, compact, complete, eventEol, endOfLine,
        PatternLayout.newSerializerBuilder().setConfiguration(config).setPattern(headerPattern).setDefaultPattern(DEFAULT_HEADER).build(),
        PatternLayout.newSerializerBuilder().setConfiguration(config).setPattern(footerPattern).setDefaultPattern(DEFAULT_FOOTER).build(),
        includeNullDelimiter,
        additionalFields);
  }

  /**
   * Returns appropriate JSON header.
   *
   * @return a byte array containing the header, opening the JSON array.
   */
  @Override
  public byte[] getHeader() {
    if (!this.complete) {
      return null;
    }
    final StringBuilder buf = new StringBuilder();
    final String str = serializeToString(getHeaderSerializer());
    if (str != null) {
      buf.append(str);
    }
    buf.append(this.eol);
    return getBytes(buf.toString());
  }

  /**
   * Returns appropriate JSON footer.
   *
   * @return a byte array containing the footer, closing the JSON array.
   */
  @Override
  public byte[] getFooter() {
    if (!this.complete) {
      return null;
    }
    final StringBuilder buf = new StringBuilder();
    buf.append(this.eol);
    final String str = serializeToString(getFooterSerializer());
    if (str != null) {
      buf.append(str);
    }
    buf.append(this.eol);
    return getBytes(buf.toString());
  }

  @Override
  public Map<String, String> getContentFormat() {
    final Map<String, String> result = new HashMap<>();
    result.put("version", "2.0");
    return result;
  }

  /**
   * @return The content type.
   */
  @Override
  public String getContentType() {
    return CONTENT_TYPE + "; charset=" + this.getCharset();
  }

  /**
   * Creates a JSON Layout.
   * @param config
   *           The plugin configuration.
   * @param locationInfo
   *            If "true", includes the location information in the generated JSON.
   * @param properties
   *            If "true", includes the thread context map in the generated JSON.
   * @param propertiesAsList
   *            If true, the thread context map is included as a list of map entry objects, where each entry has
   *            a "key" attribute (whose value is the key) and a "value" attribute (whose value is the value).
   *            Defaults to false, in which case the thread context map is included as a simple map of key-value
   *            pairs.
   * @param complete
   *            If "true", includes the JSON header and footer, and comma between records.
   * @param compact
   *            If "true", does not use end-of-lines and indentation, defaults to "false".
   * @param eventEol
   *            If "true", forces an EOL after each log event (even if compact is "true"), defaults to "false". This
   *            allows one even per line, even in compact mode.
   * @param headerPattern
   *            The header pattern, defaults to {@code "["} if null.
   * @param footerPattern
   *            The header pattern, defaults to {@code "]"} if null.
   * @param charset
   *            The character set to use, if {@code null}, uses "UTF-8".
   * @param includeStacktrace
   *            If "true", includes the stacktrace of any Throwable in the generated JSON, defaults to "true".
   * @return A JSON Layout.
   *
   * @deprecated Use {@link #newBuilder()} instead
   */
  @Deprecated
  public static ObfuscatedJsonLayout createLayout(
      final Configuration config,
      final boolean locationInfo,
      final boolean properties,
      final boolean propertiesAsList,
      final boolean complete,
      final boolean compact,
      final boolean eventEol,
      final String headerPattern,
      final String footerPattern,
      final Charset charset,
      final boolean includeStacktrace) {
    final boolean encodeThreadContextAsList = properties && propertiesAsList;
    return new ObfuscatedJsonLayout(config, locationInfo, properties, encodeThreadContextAsList, complete, compact, eventEol,
        null, headerPattern, footerPattern, charset, includeStacktrace, false, false, false, null, false);
  }

  @PluginBuilderFactory
  public static <B extends JsonLayout.Builder<B>> B newBuilder() {
    return new JsonLayout.Builder<B>().asBuilder();
  }

  /**
   * Creates a JSON Layout using the default settings. Useful for testing.
   *
   * @return A JSON Layout.
   */
  public static ObfuscatedJsonLayout createDefaultLayout() {
    return new ObfuscatedJsonLayout(new DefaultConfiguration(), false, false, false, false, false, false, null,
        DEFAULT_HEADER, DEFAULT_FOOTER, StandardCharsets.UTF_8, true, false, false, false, null, false);
  }

  @Override
  public void toSerializable(final LogEvent event, final Writer writer) throws IOException {
    if (complete && eventCount > 0) {
      writer.append(", ");
    }
    super.toSerializable(event, writer);
  }



}
