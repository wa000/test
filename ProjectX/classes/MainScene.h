#ifndef __MainScene_SCENE_H__
#define __MainScene_SCENE_H__

#include "cocos2d.h"
#include "ui\CocosGUI.h"
#include "cocostudio\CocoStudio.h"
#include "cocos2d.h"
#include "ProjectTools.h"
#include "VisibleRect.h"
#include "Player.h"
#include "PlayerBoom.h"


USING_NS_CC;
using namespace cocostudio::timeline;

class MainScene : public cocos2d::Layer
{
	public:
    // there's no 'id' in cpp, so we recommend returning the class instance pointer
    static cocos2d::Scene* createScene();

    // Here's a difference. Method 'init' in cocos2d-x returns bool, instead of returning 'id' in cocos2d-iphone
    virtual bool init();
    
    // a selector callback
    void menuCloseCallback(cocos2d::Ref* pSender);
    
    // implement the "static create()" method manually
    CREATE_FUNC(MainScene);

	bool onTouchBegan(Touch* touch, Event* event);

	void enemyAction(float f);

	void createBoom(float f);

	void removeOneSprite();

	void removePlayerBoom(float f);
private:
	EventListenerTouchOneByOne* _listener_touch;
	Player* _player;
	Player* _enemy1;
	std::vector<PlayerBoom*> _booms;
};

#endif // __MainScene_SCENE_H__
