package org.jetbrains.plugins.clojure.annotator;

import com.intellij.codeInspection.ProblemHighlightType;
import com.intellij.lang.annotation.Annotation;
import com.intellij.lang.annotation.AnnotationHolder;
import com.intellij.lang.annotation.Annotator;
import com.intellij.psi.PsiElement;
import com.intellij.util.containers.HashSet;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.plugins.clojure.ClojureBundle;
import org.jetbrains.plugins.clojure.psi.api.ClKeyword;

import java.util.Arrays;
import java.util.Set;

/**
 * @author ilyas
 */
public class ClojureAnnotator implements Annotator {
  public static final Set<String> IMPLICIT_NAMES = new HashSet<String>();

  static {
    IMPLICIT_NAMES.addAll(Arrays.asList("def", "new", "try", "throw", "catch", "finally", "ns", "in-ns", "if", "do",
        "recur", "quote", "var", "set!", "monitor-enter", "monitor-exit", "."));
  }

  public void annotate(@NotNull PsiElement element, @NotNull AnnotationHolder holder) {
    if (element instanceof ClKeyword) {
      checkKeywordTextConsistency((ClKeyword) element, holder);
    }
  }

  private void checkKeywordTextConsistency(ClKeyword keyword, AnnotationHolder holder) {
    String keywordText = keyword.getText();
    int index = keywordText.lastIndexOf("/");
    if ((index != -1 && keywordText.charAt(index - 1) == ':') || keywordText.endsWith(":") ||
        keywordText.substring(1).contains("::")) {
      Annotation annotation = holder.createErrorAnnotation(keyword, ClojureBundle.message("invalid.token", keywordText));
      annotation.setHighlightType(ProblemHighlightType.GENERIC_ERROR_OR_WARNING);
      return;
    }
  }
}
