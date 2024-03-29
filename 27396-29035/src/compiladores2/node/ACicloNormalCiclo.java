/* This file was generated by SableCC (http://www.sablecc.org/). */

package compiladores2.node;

import compiladores2.analysis.*;

@SuppressWarnings("nls")
public final class ACicloNormalCiclo extends PCiclo
{
    private PExp _l_;
    private PCorpo _r_;

    public ACicloNormalCiclo()
    {
        // Constructor
    }

    public ACicloNormalCiclo(
        @SuppressWarnings("hiding") PExp _l_,
        @SuppressWarnings("hiding") PCorpo _r_)
    {
        // Constructor
        setL(_l_);

        setR(_r_);

    }

    @Override
    public Object clone()
    {
        return new ACicloNormalCiclo(
            cloneNode(this._l_),
            cloneNode(this._r_));
    }

    @Override
    public void apply(Switch sw)
    {
        ((Analysis) sw).caseACicloNormalCiclo(this);
    }

    public PExp getL()
    {
        return this._l_;
    }

    public void setL(PExp node)
    {
        if(this._l_ != null)
        {
            this._l_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._l_ = node;
    }

    public PCorpo getR()
    {
        return this._r_;
    }

    public void setR(PCorpo node)
    {
        if(this._r_ != null)
        {
            this._r_.parent(null);
        }

        if(node != null)
        {
            if(node.parent() != null)
            {
                node.parent().removeChild(node);
            }

            node.parent(this);
        }

        this._r_ = node;
    }

    @Override
    public String toString()
    {
        return ""
            + toString(this._l_)
            + toString(this._r_);
    }

    @Override
    void removeChild(@SuppressWarnings("unused") Node child)
    {
        // Remove child
        if(this._l_ == child)
        {
            this._l_ = null;
            return;
        }

        if(this._r_ == child)
        {
            this._r_ = null;
            return;
        }

        throw new RuntimeException("Not a child.");
    }

    @Override
    void replaceChild(@SuppressWarnings("unused") Node oldChild, @SuppressWarnings("unused") Node newChild)
    {
        // Replace child
        if(this._l_ == oldChild)
        {
            setL((PExp) newChild);
            return;
        }

        if(this._r_ == oldChild)
        {
            setR((PCorpo) newChild);
            return;
        }

        throw new RuntimeException("Not a child.");
    }
}
