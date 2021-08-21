package top.koolhaas.tinybee.generator.generator.config;

import com.baomidou.mybatisplus.core.toolkit.ArrayUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.sql.StringEscape;
import com.baomidou.mybatisplus.core.toolkit.support.BiIntFunction;

import java.util.Collection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * @program: bizos
 * @description:
 * @author: hackerdom
 * @created: 2021/08/21
 */
public class BaseUtil {
    public static final String EMPTY = "";
    public static final String IS = "is";
    public static final char UNDERLINE = '_';
    public static final Pattern MP_SQL_PLACE_HOLDER = Pattern.compile("[{](?<idx>\\d+)}");
    private static final Pattern P_IS_COLUMN = Pattern.compile("^\\w\\S*[\\w\\d]*$");
    private static final Pattern CAPITAL_MODE = Pattern.compile("^[0-9A-Z/_]+$");

    public BaseUtil() {
    }

    public static boolean isBlank(CharSequence cs) {
        if (cs != null) {
            int length = cs.length();

            for (int i = 0; i < length; ++i) {
                if (!Character.isWhitespace(cs.charAt(i))) {
                    return false;
                }
            }
        }

        return true;
    }

    public static String toStringTrim(Object o) {
        return String.valueOf(o).trim();
    }

    public static boolean isNotBlank(CharSequence cs) {
        return !isBlank(cs);
    }

    public static boolean isCamel(String str) {
        return Character.isLowerCase(str.charAt(0)) && !str.contains("_");
    }

    public static boolean isNotColumnName(String str) {
        return !P_IS_COLUMN.matcher(str).matches();
    }

    public static String getTargetColumn(String column) {
        return isNotColumnName(column) ? column.substring(1, column.length() - 1) : column;
    }

    public static String camelToUnderline(String param) {
        if (isBlank(param)) {
            return "";
        } else {
            int len = param.length();
            StringBuilder sb = new StringBuilder(len);

            for (int i = 0; i < len; ++i) {
                char c = param.charAt(i);
                if (Character.isUpperCase(c) && i > 0) {
                    sb.append('_');
                }

                sb.append(Character.toLowerCase(c));
            }

            return sb.toString();
        }
    }

    public static String underlineToCamel(String param) {
        if (isBlank(param)) {
            return "";
        } else {
            String temp = param.toLowerCase();
            int len = temp.length();
            StringBuilder sb = new StringBuilder(len);

            for (int i = 0; i < len; ++i) {
                char c = temp.charAt(i);
                if (c == '_') {
                    ++i;
                    if (i < len) {
                        sb.append(Character.toUpperCase(temp.charAt(i)));
                    }
                } else {
                    sb.append(c);
                }
            }

            return sb.toString();
        }
    }

    public static String firstToLowerCase(String param) {
        return isBlank(param) ? "" : param.substring(0, 1).toLowerCase() + param.substring(1);
    }

    public static boolean matches(String regex, String input) {
        return null != regex && null != input ? Pattern.matches(regex, input) : false;
    }

    public static String sqlArgsFill(String content, Object... args) {
        if (isNotBlank(content) && ArrayUtils.isNotEmpty(args)) {
            BiIntFunction<Matcher, CharSequence> handler = (m, i) -> {
                return sqlParam(args[Integer.parseInt(m.group("idx"))]);
            };
            return replace(content, MP_SQL_PLACE_HOLDER, handler).toString();
        } else {
            return content;
        }
    }

    public static StringBuilder replace(CharSequence src, Pattern ptn, BiIntFunction<Matcher, CharSequence> replacer) {
        int idx = 0;
        int last = 0;
        int len = src.length();
        Matcher m = ptn.matcher(src);

        StringBuilder sb;
        for (sb = new StringBuilder(); m.find(); last = m.end()) {
            sb.append(src, last, m.start()).append((CharSequence) replacer.apply(m, idx++));
        }

        if (last < len) {
            sb.append(src, last, len);
        }

        return sb;
    }

    public static String sqlParam(Object obj) {
        String repStr;
        if (obj instanceof Collection) {
            repStr = quotaMarkList((Collection) obj);
        } else {
            repStr = quotaMark(obj);
        }

        return repStr;
    }

    public static String quotaMark(Object obj) {
        String srcStr = String.valueOf(obj);
        return obj instanceof CharSequence ? StringEscape.escapeString(srcStr) : srcStr;
    }

    public static String quotaMarkList(Collection<?> coll) {
        return (String) coll.stream().map(StringUtils::quotaMark).collect(Collectors.joining(",", "(", ")"));
    }

    public static String concatCapitalize(String concatStr, final String str) {
        if (isBlank(concatStr)) {
            concatStr = "";
        }

        if (str != null && str.length() != 0) {
            char firstChar = str.charAt(0);
            return Character.isTitleCase(firstChar) ? str : concatStr + Character.toTitleCase(firstChar) + str.substring(1);
        } else {
            return str;
        }
    }

    public static boolean checkValNotNull(Object object) {
        if (object instanceof CharSequence) {
            return isNotBlank((CharSequence) object);
        } else {
            return object != null;
        }
    }

    public static boolean checkValNull(Object object) {
        return !checkValNotNull(object);
    }

    public static boolean containsUpperCase(String word) {
        for (int i = 0; i < word.length(); ++i) {
            char c = word.charAt(i);
            if (Character.isUpperCase(c)) {
                return true;
            }
        }

        return false;
    }

    public static boolean isCapitalMode(String word) {
        return null != word && CAPITAL_MODE.matcher(word).matches();
    }

    public static boolean isMixedMode(String word) {
        return matches(".*[A-Z]+.*", word) && matches(".*[/_]+.*", word);
    }

    public static boolean endsWith(String str, String suffix) {
        return endsWith(str, suffix, false);
    }

    private static boolean endsWith(String str, String suffix, boolean ignoreCase) {
        if (str != null && suffix != null) {
            if (suffix.length() > str.length()) {
                return false;
            } else {
                int strOffset = str.length() - suffix.length();
                return str.regionMatches(ignoreCase, strOffset, suffix, 0, suffix.length());
            }
        } else {
            return str == null && suffix == null;
        }
    }

    public static boolean isCharSequence(Class<?> clazz) {
        return clazz != null && CharSequence.class.isAssignableFrom(clazz);
    }

    public static String prefixToLower(String rawString, int index) {
        String beforeChar = rawString.substring(0, index).toLowerCase();
        String afterChar = rawString.substring(index);
        return beforeChar + afterChar;
    }

    public static String removePrefixAfterPrefixToLower(String rawString, int index) {
        return prefixToLower(rawString.substring(index), 1);
    }

    public static String camelToHyphen(String input) {
        return wordsToHyphenCase(wordsAndHyphenAndCamelToConstantCase(input));
    }

    private static String wordsAndHyphenAndCamelToConstantCase(String input) {
        StringBuilder buf = new StringBuilder();
        char previousChar = ' ';
        char[] chars = input.toCharArray();
        char[] var4 = chars;
        int var5 = chars.length;

        for (int var6 = 0; var6 < var5; ++var6) {
            char c = var4[var6];
            boolean isUpperCaseAndPreviousIsLowerCase = Character.isLowerCase(previousChar) && Character.isUpperCase(c);
            boolean previousIsWhitespace = Character.isWhitespace(previousChar);
            boolean lastOneIsNotUnderscore = buf.length() > 0 && buf.charAt(buf.length() - 1) != '_';
            boolean isNotUnderscore = c != '_';
            if (!lastOneIsNotUnderscore || !isUpperCaseAndPreviousIsLowerCase && !previousIsWhitespace) {
                if (Character.isDigit(previousChar) && Character.isLetter(c)) {
                    buf.append('_');
                }
            } else {
                buf.append("_");
            }

            if (shouldReplace(c) && lastOneIsNotUnderscore) {
                buf.append('_');
            } else if (!Character.isWhitespace(c) && (isNotUnderscore || lastOneIsNotUnderscore)) {
                buf.append(Character.toUpperCase(c));
            }

            previousChar = c;
        }

        if (Character.isWhitespace(previousChar)) {
            buf.append("_");
        }

        return buf.toString();
    }

    private static boolean shouldReplace(char c) {
        return c == '.' || c == '_' || c == '-';
    }

    private static String wordsToHyphenCase(String s) {
        StringBuilder buf = new StringBuilder();
        char lastChar = 'a';
        char[] var3 = s.toCharArray();
        int var4 = var3.length;

        for (int var5 = 0; var5 < var4; ++var5) {
            char c = var3[var5];
            if (Character.isWhitespace(lastChar) && !Character.isWhitespace(c) && '-' != c && buf.length() > 0 && buf.charAt(buf.length() - 1) != '-') {
                buf.append("-");
            }

            if ('_' == c) {
                buf.append('-');
            } else if ('.' == c) {
                buf.append('-');
            } else if (!Character.isWhitespace(c)) {
                buf.append(Character.toLowerCase(c));
            }

            lastChar = c;
        }

        if (Character.isWhitespace(lastChar)) {
            buf.append("-");
        }

        return buf.toString();
    }
}
