#include "Player.h"

/**
* ��������: ����һ��Player���ָ��,�ǲο�CREATE_FUNC����ʵ�ֵġ�
* ���ߣ�wa000
* ���ڣ�2014
*/ 
Player* Player::create(PlayerType type)
{
	Player* player = new Player();
	if(player && player->initWithPlayerType(type))
	{
		player->autorelease();
		return player;
	}
	else
	{
		delete player;
		player = NULL;
		return NULL;
	}
}

/**
* ��������: �������ͽ��г�ʼ���ķ���
* ���ߣ�wa000
* ���ڣ�2014
*/ 
bool Player::initWithPlayerType(PlayerType type)
{
	_speed = VisibleRect::getVisibleRect().size.width / 5;
	_onHit = 0;

	switch (type)
	{
	case Player::PLAYER:
		{
			_name = "Player(60,100)";
			_animationNum = 3;
			//����ƴͼƬ���Ƶ�����
			int animationFrameNum[3] = {1, 2, 4};
			_animationFrameNum.assign(animationFrameNum, animationFrameNum+3);
			std::string animationNames[3] = {"stand","walkup","walkright"};
			_animationNames.assign(animationNames, animationNames+3);
			this->initWithSpriteFrameName("Player(60,100)-stand-1.png");
			break;
		}
	case Player::ENEMY1:
		{
			_name = "enemy(80,140)";
			_animationNum = 3;
			int animationFrameNum[3] = {1, 2, 3};
			_animationFrameNum.assign(animationFrameNum, animationFrameNum+3);
			std::string animationNames[3] = {"stand", "walkright", "hit"};
			_animationNames.assign(animationNames, animationNames+3);
			this->initWithSpriteFrameName("enemy(80,140)-stand-1.png");
			break;
		}
	default:
		break;
	}
	
	this->addAnimation();
	this->playAnimationForever(Player::PlayerAction::STAND);

	return true;
}

/**
* ��������: ����ɫ�������뻺��
* ���ߣ�wa000
* ���ڣ�2014
*/ 
void Player::addAnimation()
{
	//�ж��Ƿ��Ѿ�����ͼƬ��Դ
	auto animation = AnimationCache::getInstance()->getAnimation(
		cocos2d::String::createWithFormat("%s-%s", _name.c_str(), _animationNames[0].c_str())->getCString());
	if(animation)
	{
		return;
	}

	//����ͼƬ��Դ
	for(int i = 0; i < _animationNum; i++)
	{
		auto animation = Animation::create();
		//����ÿ֡���ʱ��
		animation->setDelayPerUnit(0.2f);
		//����ÿһ֡
		for(int j = 0; j<_animationFrameNum[i]; j++)
		{
			auto sfName = cocos2d::String::createWithFormat("%s-%s-%d.png", _name.c_str(), _animationNames[i].c_str(), j+1)->getCString();
			animation->addSpriteFrame(SpriteFrameCache::getInstance()->getSpriteFrameByName(sfName));
		}
		AnimationCache::getInstance()->addAnimation(
			animation, String::createWithFormat("%s-%s", _name.c_str(), _animationNames[i].c_str())->getCString());
	}
}

/**
* ��������: ִ�н�ɫ�Ķ���
* ���ߣ�wa000
* ���ڣ�2014
*/ 
void Player::playAnimationForever(int index)
{
	//�жϵ�ǰ�Ƿ���ִ����Ҫִ�еĶ������������ִ�о�ֱ�ӷ��ء�
	auto act = this->getActionByTag(index);
	if(act)
	{
		return;
	}

	//����ǰ�Ķ������
	for(int i = 0; i<_animationNum; i++)
	{
		this->stopActionByTag(i);
	}

	if(index < 0 || index >= _animationNum)
	{
		return;
	}

	auto str = String::createWithFormat("%s-%s", _name.c_str(), _animationNames[index].c_str())->getCString();
	auto animation = AnimationCache::getInstance()->getAnimation(str);
	auto animate = RepeatForever::create(Animate::create(animation));
	animate->setTag(index);
	this->runAction(animate);
}

/**
* ��������: point����Ҫ�ƶ���������
* ���ߣ�wa000
* ���ڣ�2014
*/ 
void Player::walkTo(Vec2 point)
{
	this->stopActionByTag(WALK_TAG);
	auto curPos = this->getPosition();

	//�ж�Ҫ�����λ���ڵ�ǰλ�õ���߻����ұߣ����������߾ͽ����鷭ת�����ﵽ
	//ת���Ч����
	if(point.x > curPos.x)
	{
		this->setFlippedX(false);
	}
	else
	{
		this->setFlippedX(true);
	}
	
	auto diff = point - curPos;
	//����ǰ��ʱ��
	auto time = diff.getLength() / _speed;
	auto move = MoveTo::create(time, point);
	//����ִ����MoveTo������Ļص�����
	auto func = [&]()
	{
		this->stopActionByTag(WALK_TAG);
		this->playAnimationForever(Player::PlayerAction::STAND);
	};
	auto callback = CallFunc::create(func);
	auto seq = Sequence::create(move, callback, nullptr);
	seq->setTag(WALK_TAG);
	this->runAction(seq);
	//���Ҫ�ߵ���λ��Ϊ��ǰλ�õ��Ϸ�����ִ�������ߵĶ�����
	if(_name == "Player(60,100)")
	{
		if(std::fabs(curPos.x - point.x) < 100)
		{
			this->playAnimationForever(Player::PlayerAction::WALKUP);
		}
		else
		{
			this->playAnimationForever(Player::PlayerAction::WALKRIGHT);
		}
	}
	if(_name == "enemy(80,140)")
	{
		this->playAnimationForever(Player::EnemyAction::ENEMYWALKRIGHT);
	}
}

int Player::onHit()
{
	return _onHit;
}

void Player::setOnHit(int i)
{
	_onHit = i;
}