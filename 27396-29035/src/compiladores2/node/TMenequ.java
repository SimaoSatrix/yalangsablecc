/* This file was generated by SableCC (http://www.sablecc.org/). */

package compiladores2.node;

import compiladores2.analysis.*;

@SuppressWarnings("nls")
public final class TMenequ extends Token
{
    public TMenequ(String text)
    {
        setText(text);
    }

    public TMenequ(String text, int line, int pos)
    {
        setText(text);
        setLine(line);
        setPos(pos);
    }

    @Override
    public Object clone()
    {
      return new TMenequ(getText(), getLine(), getPos());
    }

    @Override
    public void apply(Switch sw)
    {
        ((Analysis) sw).caseTMenequ(this);
    }
}
