---
layout: page
title: 你的程序人生和我一样吗
titlebar: life
subtitle: <span class="mega-octicon octicon-organization"></span>&nbsp;&nbsp; 微信搜索“沉默王二”，回复关键字“程序员”获取二哥肝了三天三夜的《程序员不可或缺的软技能》PDF
menu: life
css: ['blog-page.css']
permalink: /life
---

<div class="row">

    <div class="col-md-12">

        <ul id="posts-list">
            {% for post in site.posts %}
                {% if post.category=='life' %}
                <li class="posts-list-item">
                    <div class="posts-content">
                        <span class="posts-list-meta">{{ post.date | date: "%Y-%m-%d" }}</span>
                        <a class="posts-list-name bubble-float-left" href="{{ post.url }}">{{ post.title }}</a>
                        <span class='circle'></span>
                    </div>
                </li>
                {% endif %}
            {% endfor %}
        </ul> 

        <!-- Pagination -->
        {% include pagination.html %}

        <!-- Comments -->
       <div class="comment">
         {% include comments.html %}
       </div>
    </div>

</div>
<script>
    $(document).ready(function(){

        // Enable bootstrap tooltip
        $("body").tooltip({ selector: '[data-toggle=tooltip]' });

    });
</script>
