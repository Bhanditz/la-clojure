package org.jetbrains.plugins.clojure.debugger;

import com.intellij.debugger.PositionManager;
import com.intellij.debugger.PositionManagerFactory;
import com.intellij.debugger.engine.DebugProcess;
import org.jetbrains.annotations.Nullable;

/**
 * @author Alefas
 * @since 20.11.12
 */
public class ClojurePositionManagerFactory extends PositionManagerFactory {
  @Nullable
  @Override
  public PositionManager createPositionManager(DebugProcess process) {
    return new ClojurePositionManager(process);
  }
}
