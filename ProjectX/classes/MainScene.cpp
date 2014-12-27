#include "MainScene.h"

USING_NS_CC;

Scene* MainScene::createScene()
{
    // 'scene' is an autorelease object
    auto scene = Scene::create();
    
    // 'layer' is an autorelease object
    auto layer = MainScene::create();

    // add layer as a child to scene
    scene->addChild(layer);

    // return the scene
    return scene;
}

// on "init" you need to initialize your instance
bool MainScene::init()
{
    //////////////////////////////
    // 1. super init first
    if ( !Layer::init() )
    {
        return false;
    }
    
    Size visibleSize = Director::getInstance()->getVisibleSize();
    Vec2 origin = Director::getInstance()->getVisibleOrigin();

    /////////////////////////////
    // 2. add a menu item with "X" image, which is clicked to quit the program
    //    you may modify it.

    // add a "close" icon to exit the progress. it's an autorelease object
    auto closeItem = MenuItemImage::create(
                                           "CloseNormal.png",
                                           "CloseSelected.png",
                                           CC_CALLBACK_1(MainScene::menuCloseCallback, this));

	ProjectTool::setSpriteScale(closeItem, 0.2, Vec2(40, 40));

	closeItem->setPosition(VisibleRect::rightBottom().x * 0.9, VisibleRect::getVisibleRect().size.height * 0.1);

    // create menu, it's an autorelease object
    auto menu = Menu::create(closeItem, NULL);
    menu->setPosition(Vec2::ZERO);
    this->addChild(menu, 1);

    /////////////////////////////
    // 3. add your codes below...
    
	//加载角色图片资源
	SpriteFrameCache::getInstance()->addSpriteFramesWithFile("player/Player.plist","player/Player.png");
	SpriteFrameCache::getInstance()->addSpriteFramesWithFile("enemy/enemy.plist", "enemy/enemy.png");
	SpriteFrameCache::getInstance()->addSpriteFramesWithFile("boom/boom.plist", "boom/boom.png");

    Sprite* background = Sprite::create("background/bg(640,400)_1.png");
    background->setPosition(Vec2(VisibleRect::center()));
	ProjectTool::setBackgroundScale(background, Vec2(640, 400));
	this->addChild(background);

	_player = Player::create(Player::PlayerType::PLAYER);
	_player->setPosition(VisibleRect::center());
	ProjectTool::setSpriteScale(_player, 0.1, Vec2(60, 100));
	this->addChild(_player);

	_enemy1 = Player::create(Player::PlayerType::ENEMY1);
	_enemy1->setPosition(VisibleRect::rightTop());
	ProjectTool::setSpriteScale(_enemy1, 0.15, Vec2(80, 140));
	this->addChild(_enemy1);

	this->schedule(schedule_selector(MainScene::enemyAction), 0.1);
	this->schedule(schedule_selector(MainScene::removePlayerBoom), 0.1);

	_listener_touch = EventListenerTouchOneByOne::create();
	_listener_touch->onTouchBegan = CC_CALLBACK_2(MainScene::onTouchBegan, this);
	_eventDispatcher->addEventListenerWithSceneGraphPriority(_listener_touch, this);

    return true;
}


void MainScene::menuCloseCallback(Ref* pSender)
{
#if (CC_TARGET_PLATFORM == CC_PLATFORM_WP8) || (CC_TARGET_PLATFORM == CC_PLATFORM_WINRT)
	MessageBox("You pressed the close button. Windows Store Apps do not implement a close button.","Alert");
    return;
#endif

    Director::getInstance()->end();

#if (CC_TARGET_PLATFORM == CC_PLATFORM_IOS)
    exit(0);
#endif
}

bool MainScene::onTouchBegan(Touch* touch, Event* event)
{
	Vec2 pos = this->convertToNodeSpace(touch->getLocation());
	_player->walkTo(pos);
	return true;
}

void MainScene::enemyAction(float f)
{
	//如果正在释放技能不能进行其他动作
	if(_enemy1->onHit() >= 1)
	{
		return;
	}
	Vec2 playerPos = _player->getPosition();
	Vec2 enemyPos = _enemy1->getPosition();
	auto diff = playerPos - enemyPos;
	if(diff.length() < (VisibleRect::getVisibleRect().size.width / 5))
	{
		_enemy1->stopActionByTag(Player::Action_Tag::WALK_TAG);
		_enemy1->playAnimationForever(Player::EnemyAction::ENEMYHIT);
		_enemy1->setOnHit(1);
		this->scheduleOnce(schedule_selector(MainScene::createBoom), 1);
		return;
	}
	_enemy1->walkTo(playerPos);
}

void MainScene::createBoom(float f)
{
	auto boom = PlayerBoom::create();
	boom->setPosition(_enemy1->getPosition());
	boom->walkTo(_player->getPosition());
	ProjectTool::setSpriteScale(boom, 0.05, Vec2(400,400));
	boom->playAnimationForever(1001);
	this->addChild(boom);
	_booms.push_back(boom);
	_enemy1->setOnHit(0);
}

/**
* 函数介绍: 定时移除_booms中的对象
* 作者：wa000
* 日期：2014
*/ 
void MainScene::removePlayerBoom(float f)
{

	for(auto it = _booms.begin(); it != _booms.end(); it++)
	{
		if((*it)->getFinished() == 1)
		{
			this->removeChild(*it);
			it = _booms.erase(it);
		}
	}
}