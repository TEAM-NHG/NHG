<script setup>
defineProps({ article: Object });
</script>

<template>
  <tr class="text-center">
    <th scope="row">{{ article.articleNo }}</th>
    <td class="text-start">
      <router-link
        :to="{ name: 'article-view', params: { articleNo: article.articleNo } }"
        class="article-title link-dark"
      >
        {{ article.subject }}
      </router-link>
    </td>
    <td>{{ article.userId }}</td>
    <td>{{ article.hit }}</td>
    <td>{{ article.registerTime.replace(/T.*/, "") }}</td>
  </tr>
</template>

<style scoped>
a {
  text-decoration: none;
}

.text-center{
  height: 42px;
  line-height: 42px;
}
</style>
