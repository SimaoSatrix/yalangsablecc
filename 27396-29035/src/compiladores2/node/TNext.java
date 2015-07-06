/* This file was generated by SableCC (http://www.sablecc.org/). */

package compiladores2.node;

import compiladores2.analysis.*;

@SuppressWarnings("nls")
public final class TNext extends Token
{
    public TNext()
    {
        super.setText("next");
    }

    public TNext(int line, int pos)
    {
        super.setText("next");
        setLine(line);
        setPos(pos);
    }

    @Override
    public Object clone()
    {
      return new TNext(getLine(), getPos());
    }

    @Override
    public void apply(Switch sw)
    {
        ((Analysis) sw).caseTNext(this);
    }

    @Override
    public void setText(@SuppressWarnings("unused") String text)
    {
        throw new RuntimeException("Cannot change TNext text.");
    }
}
