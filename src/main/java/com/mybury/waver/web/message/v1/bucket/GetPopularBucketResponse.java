package com.mybury.waver.web.message.v1.bucket;

import com.mybury.waver.domain.Bucket;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public record GetPopularBucketResponse(
    List<String> popularKeyword,
    List<PopularBucketElement> popularList,
    List<String> recommendKeyword,
    List<PopularBucketElement> recommendList
){

    public static GetPopularBucketResponse of(List<Bucket> popularBucketList,List<Bucket> recommendBucketList){
        List<PopularBucketElement> popularElements = popularBucketList.stream().limit(4).map(PopularBucketElement::new).toList();
        List<PopularBucketElement> recommendElements = recommendBucketList.stream().limit(4).map(PopularBucketElement::new).toList();

        List<String> popularKeywords = extractKeywordNames(popularElements);
        List<String> recommendKeywords = extractKeywordNames(recommendElements);

        return new GetPopularBucketResponse(popularKeywords, popularElements, recommendKeywords, recommendElements);
    }

    private static List<String> extractKeywordNames(List<PopularBucketElement> elements) {
        if (elements == null || elements.isEmpty()) {
            return List.of();
        }

        Set<String> keywordNames = elements.stream()
            .flatMap(element -> element.keyword().stream())
            .map(KeywordElement::name)
            .collect(LinkedHashSet::new, LinkedHashSet::add, LinkedHashSet::addAll);

        return keywordNames.stream().limit(4).toList();
    }
}
