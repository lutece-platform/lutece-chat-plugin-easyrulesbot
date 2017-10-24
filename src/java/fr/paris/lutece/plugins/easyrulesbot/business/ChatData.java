/*
 * Copyright (c) 2002-2017, Mairie de Paris
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


package fr.paris.lutece.plugins.easyrulesbot.business;

import fr.paris.lutece.plugins.easyrulesbot.business.BotExecutor;
import fr.paris.lutece.plugins.easyrulesbot.business.Post;
import fr.paris.lutece.plugins.easyrulesbot.service.BotService;
import java.util.ArrayList;
import java.util.List;

/**
 * ChatData
 */
public class ChatData 
{
    private List<Post> _listPosts;
    BotExecutor _executor;
    
    public ChatData( String strBotKey )
    {
        _listPosts = new ArrayList<Post>( );
        _executor = BotService.getExecutor( strBotKey );
    }
    
    public BotExecutor getExecutor()
    {
        return _executor;
    }
    
    /**
     * Add a post
     * 
     * @param post
     *            The post
     */
    public void addPost( Post post )
    {
        _listPosts.add( post );
    }

    /**
     * Add a post
     * 
     * @param strContent
     *            The content
     */
    public void addBotPost( String strContent )
    {
        addPost( strContent, Post.AUTHOR_BOT );
    }

    /**
     * Add a post
     * 
     * @param strContent
     *            The content
     */
    public void addUserPost( String strContent )
    {
        addPost( strContent, Post.AUTHOR_USER );
    }

    /**
     * Add a post
     * 
     * @param strContent
     *            The content
     * @param nAuthor
     *            The author
     */
    private void addPost( String strContent, int nAuthor )
    {
        Post post = new Post( );
        post.setContent( strContent );
        post.setAuthor( nAuthor );
        _listPosts.add( post );
    }

    /**
     * The post list
     * 
     * @return The list of all posts
     */
    public List<Post> getPosts( )
    {
        return _listPosts;
    }
    
            
}
