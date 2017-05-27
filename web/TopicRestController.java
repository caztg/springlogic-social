package cn.springlogic.social.web;

import cn.springlogic.blog.web.BlogRestController;
import cn.springlogic.social.jpa.entity.Follow;
import cn.springlogic.social.jpa.entity.Publication;
import cn.springlogic.social.jpa.entity.PublicationFavor;
import cn.springlogic.social.jpa.entity.Topic;
import cn.springlogic.social.jpa.repository.FollowRepository;
import cn.springlogic.social.jpa.repository.PublicationFavorRepository;
import cn.springlogic.social.jpa.repository.PublicationRepository;
import cn.springlogic.social.jpa.repository.TopicRepository;
import cn.springlogic.social.util.SortListUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.PersistentEntityResource;
import org.springframework.data.rest.webmvc.PersistentEntityResourceAssembler;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by admin on 2017/5/11.
 */
@RepositoryRestController
@RequestMapping(value = "social:topics")
public class TopicRestController {


    @Autowired
    private PagedResourcesAssembler pagedResourcesAssembler;

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private PublicationRepository publicationRepository;

    /**
     * 后台 所有标签
     *
     * @param topic
     * @param pageable
     * @param resourceAssembler
     * @return
     */
    @ResponseBody
    @GetMapping(value = "/search/all")
    public ResponseEntity<PagedResources<PersistentEntityResource>> topicSearch(@RequestParam(name = "topic", required = false, defaultValue = "") String topic,
                                                                                @RequestParam(name = "nick_name", required = false) String nickName,
                                                                                @RequestParam(name = "description", required = false) String description,
                                                                                @RequestParam(name = "time_sort", required = false) String timeSort,
                                                                                @RequestParam(name = "publication_sort", required = false) String publicationSort,
                                                                                @RequestParam(name = "favor_sort", required = false) String favorSort,
                                                                                Pageable pageable,
                                                                                PersistentEntityResourceAssembler resourceAssembler) {

        Page<Topic> page = null;

        /*   处理nick_name 搜索问题,如果为 后台系统创建的topic, user_id是为null 的
         *   但是在后台页面要显示为 "系统" ,如果此时管理员搜索 系统创建的topic时,需要调用该接口
         */
        if ("系统".equals(nickName)) {
            page = topicRepository.getAdminTopicDESC(topic, description, pageable);

            if ("ASC".equalsIgnoreCase(timeSort)) {
                page = topicRepository.getAdminTopicASC(topic, description, pageable);
            }

        } else {

            //查询出所有的饭圈列表 默认按时间最先
            page = topicRepository.getTopics(topic, nickName, description, pageable);
            if ("ASC".equalsIgnoreCase(timeSort)) {
                page = topicRepository.getTopicsASC(topic, nickName, description, pageable);
            }
        }

        //通过工具类转化器组装 发帖数 和点赞数
        Page<Topic> topicPage = page.map(new TopicRestController.TopicsConverter(publicationRepository));
         /*根据评论数或者点赞数排序处理
           不可以直接对 getContent()拿出来的 list集合进行处理
           UnsupportedOperationException. 其实List结构按是否可修改也是可以在分为两个类型的
           这里拿到的是不可修改类型,所以只能clone一个同样的list集合
         */
        List<Topic> tempList = topicPage.getContent();
        //复制一个list
        List<Topic> t = new ArrayList<Topic>(tempList);

        if (publicationSort != null) {
            //发帖总数排序默认DESC
            Collections.sort(t, new SortListUtils<Topic>("getPublicationNums", SortListUtils.DESC));
            if ("ASC".equalsIgnoreCase(publicationSort)) {
                Collections.sort(t, new SortListUtils<Topic>("getPublicationNums", SortListUtils.ASC));
            }
        }
        //点赞总数排序
        if (favorSort != null) {
            if ("DESC".equalsIgnoreCase(favorSort)) {
                Collections.sort(t, new SortListUtils<Topic>("getFavorNums", SortListUtils.DESC));
            } else {
                Collections.sort(t, new SortListUtils<Topic>("getFavorNums", SortListUtils.ASC));
            }
        }
        /**该pageable为传过来的pageable对象, 如果用sort=creatTime,desc排序,会打乱已经(点赞数,发帖数)排好的顺序,所以自定义参数time_sort进行排序*/
        Page<Topic> p = new PageImpl<Topic>(t, pageable, tempList.size());

        return ResponseEntity.ok(pagedResourcesAssembler.toResource(p, resourceAssembler));
    }


    /**
     * 转换器类
     */
    private static final class TopicsConverter implements Converter<Topic, Topic> {

        private final PublicationRepository publicationRepository;

        private TopicsConverter(PublicationRepository publicationRepository) {
            this.publicationRepository = publicationRepository;

        }

        @Override
        public Topic convert(Topic source) {

            source.setPublicationNums(source.getTags().size());
            List<Publication> listPublication = publicationRepository.findListByAll(source.getName());
            //统计总点赞数
            int sum = 0;
            for (Publication p : listPublication) {
                int publicationFavorsTotal = p.getPublicationFavors().size();
                sum = sum + publicationFavorsTotal;
            }
            source.setFavorNums(sum);

            return source;
        }
    }

}
