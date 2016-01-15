'use strict';

describe('Factory: TagService', function() {

  // load the module
  beforeEach(module('hkgApp'));

  // inject the factory called "TagService"
  var $tagService;
  beforeEach(inject(function (_TagService_) {
      $tagService = _TagService_;
  }));

  it('test defined methods', function() {
      expect($tagService.parseURL).toBeDefined();
      expect($tagService.parseYoutube).toBeDefined();
  });

  var content, result;
  it('Parse a single URL tag', function() {
      content = "Google啦: [url]http://www.google.com[/url] 幫到你";
      result = 'Google啦: <a href="http://www.google.com">http://www.google.com</a> 幫到你';
      expect($tagService.parseURL(content)).toEqual(result);
  })

  it('Parse multiple tags', function() {
      content = "J啦望: [url]http://www.google.com.hk[/url] 做咩啫你[url]http://www.facebook.com[/url]";
      result = 'J啦望: <a href="http://www.google.com.hk">http://www.google.com.hk</a> 做咩啫你<a href="http://www.facebook.com">http://www.facebook.com</a>';
      expect($tagService.parseURL(content)).toEqual(result);
  });

  it('Parse a single Youtube tag', function() {
      content = "[url]https://youtu.be/Lyak7VQhW4o[/url] 邵氏有份製作，香港拍攝"
      result = '<iframe id="ytplayer" type="text/html" class="youtubePreview" src="https://www.youtube.com/embed/Lyak7VQhW4o" frameborder="0" allowfullscreen></iframe><br><a href="https://youtu.be/Lyak7VQhW4o">https://youtu.be/Lyak7VQhW4o</a> 邵氏有份製作，香港拍攝'
      expect($tagService.parseYoutube(content)).toEqual(result);
  })

  it('Parse multiple Youtube tags', function() {
      content = "[url]https://youtu.be/Lyak7VQhW4o[/url] 邵氏有份製作，香港拍攝 [url]https://youtu.be/WxRChVF0q9U[/url] 以今日既拍攝同剪接科技，相信效果會比當時好得多，成本可以低過整3D game";
      result = '<iframe id="ytplayer" type="text/html" class="youtubePreview" src="https://www.youtube.com/embed/Lyak7VQhW4o" frameborder="0" allowfullscreen></iframe><br><a href="https://youtu.be/Lyak7VQhW4o">https://youtu.be/Lyak7VQhW4o</a> 邵氏有份製作，香港拍攝'
      + ' <iframe id="ytplayer" type="text/html" class="youtubePreview" src="https://www.youtube.com/embed/WxRChVF0q9U" frameborder="0" allowfullscreen></iframe><br><a href="https://youtu.be/WxRChVF0q9U">https://youtu.be/WxRChVF0q9U</a> 以今日既拍攝同剪接科技，相信效果會比當時好得多，成本可以低過整3D game';
      expect($tagService.parseYoutube(content)).toEqual(result);
  })

});
