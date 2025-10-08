package com.code.tama.triggerapi.botiutils;

public interface IHelpWithFBOs {
    boolean getIsStencilBufferEnabled();
    
    void setIsStencilBufferEnabledAndReload(boolean cond);
}