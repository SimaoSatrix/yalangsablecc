/* This file was generated by SableCC (http://www.sablecc.org/). */

package compiladores2.node;

import compiladores2.analysis.*;

@SuppressWarnings("nls")
public final class AVariavelExp extends PExp
{
    private TVariavel _variavel_;

    public AVariavelExp()
    {
        // Constructor
    }

    public AVariavelExp(
        @SuppressWarnings("hiding") TVariavel _variavel_)
    {
        // Constructor
        setVariavel(_variavel_);

    }

    @Override
    public Object clone()
    {
        return new AVariavelExp(
            cloneNode(this._variavel_));
    }

    @Override
    public void apply(Switch sw)
    {
        ((Analysis) sw).caseAVariavelExp(this);
    }

    public TVariavel getVariavel()
    {
        return this._variavel_;
    }

    public void setVariavel(TVariavel node)
    {
        if(this._variavel_ != null)
        {
            this._variavel_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._variavel_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._variavel_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._variavel_ == child)
        {
            this._variavel_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._variavel_ == oldChild)
        {
            setVariavel((TVariavel) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}
