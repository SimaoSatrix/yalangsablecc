/* This file was generated by SableCC (http://www.sablecc.org/). */

package compiladores2.node;

import compiladores2.analysis.*;

@SuppressWarnings("nls")
public final class ANominalExp extends PExp
{
    private TStringProd _stringProd_;

    public ANominalExp()
    {
        // Constructor
    }

    public ANominalExp(
        @SuppressWarnings("hiding") TStringProd _stringProd_)
    {
        // Constructor
        setStringProd(_stringProd_);

    }

    @Override
    public Object clone()
    {
        return new ANominalExp(
            cloneNode(this._stringProd_));
    }

    @Override
    public void apply(Switch sw)
    {
        ((Analysis) sw).caseANominalExp(this);
    }

    public TStringProd getStringProd()
    {
        return this._stringProd_;
    }

    public void setStringProd(TStringProd node)
    {
        if(this._stringProd_ != null)
        {
            this._stringProd_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._stringProd_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._stringProd_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._stringProd_ == child)
        {
            this._stringProd_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._stringProd_ == oldChild)
        {
            setStringProd((TStringProd) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}