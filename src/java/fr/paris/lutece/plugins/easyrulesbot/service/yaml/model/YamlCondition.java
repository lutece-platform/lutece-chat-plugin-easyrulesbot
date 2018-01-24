/*
 * Copyright (c) 2002-2018, Mairie de Paris
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  1. Redistributions of source code must retain the above copyright notice
 *     and the following disclaimer.
 *
 *  2. Redistributions in binary form must reproduce the above copyright notice
 *     and the following disclaimer in the documentation and/or other materials
 *     provided with the distribution.
 *
 *  3. Neither the name of 'Mairie de Paris' nor 'Lutece' nor the names of its
 *     contributors may be used to endorse or promote products derived from
 *     this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 * License 1.0
 */

package fr.paris.lutece.plugins.easyrulesbot.service.yaml.model;

import java.util.List;

/**
 * YamlCondition
 */
public class YamlCondition
{
    // Variables declarations
    private String _strCondition;
    private List<String> _listParameters;


    /**
     * Returns the Condition
     * 
     * @return The Condition
     */
    public String getCondition( )
    {
        return _strCondition;
    }

    /**
     * Sets the Condition
     * 
     * @param strCondition
     *            The Condition
     */
    public void setCondition( String strCondition )
    {
        _strCondition = strCondition;
    }

   /**
    * Returns the Parameters
    * @return The Parameters
    */ 
    public List<String> getParameters()
    {
        return _listParameters;
    }

   /**
    * Sets the Parameters
    * @param listParameters The Parameters
    */ 
    public void setParameters( List<String> listParameters )
    {
        _listParameters = listParameters;
    }

    /**
     * {@inheritDoc }
     */
    @Override
    public String toString()
    {
        StringBuilder sbOutput = new StringBuilder( "\n     CONDITION");
        sbOutput.append( "\n     name : " ).append( _strCondition );
        sbOutput.append( "\n     parameters : " );
        if( _listParameters != null )
        {
            for( String strParameter : _listParameters )
            {
                sbOutput.append( " " ).append( strParameter );
            }
        }
        return sbOutput.toString();
    }
    
}
